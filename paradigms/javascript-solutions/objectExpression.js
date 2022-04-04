"use strict";

const vars = new Map([["x", 0], ["y", 1], ["z", 2]]);

function createConstVar(evaluate, diff){
	return function(x){
		this.x = x;
	
		this.evaluate = evaluate(this.x);
		this.toString = () => x + "";
		this.prefix = () => x + "";
		this.postfix = () => x + "";
		this.diff = diff(this.x);
	}
}

const Const = createConstVar(a => () => a, a => () => new Const(0));

const Variable = createConstVar(a => (...args) => args[vars.get(a)],
	a => b => {
		if (a === b){
			return new Const(1);
		} else {
			return new Const(0);
		}
	}
);

function createAbstractOp(){
	let AbstractOp = function(evl, oper, fDiff, ...args){
		this.args = args;
	}
	AbstractOp.prototype = {
		toString : function(){
			return this.args.map(i => i.toString()).join(" ") + " " + this.oper;
		},
		prefix : function(){
			return "(" + this.oper + " " + this.args.map(i => i.prefix()).join(" ") + ")";
		},
		postfix : function(){
			return "(" + this.args.map(i => i.postfix()).join(" ") + " " + this.oper + ")";
		},
		diff : function(a){
			return this.fDiff(a, ...this.args, ...this.args.map(i => i.diff(a)));
		},
		evaluate : function(...argsIn){
			return this.evl(...this.args.map(i => i.evaluate(...argsIn)));
		}
	}
	return AbstractOp;
}

function createOp(evl, oper, fDiff){
	let abs = createAbstractOp();
	let newOp = function(...args){
		abs.call(this, evl, oper, fDiff, ...args);
	}
	newOp.prototype = Object.create(abs.prototype);
	newOp.prototype.evl = evl;
	newOp.prototype.oper = oper;
	newOp.prototype.fDiff = fDiff;
	return newOp;
}

const Add = createOp((a, b) => a + b, "+", (a, op1, op2, diff1, diff2) => new Add(diff1, diff2));

const Subtract = createOp((a, b) => a - b, "-", (a, op1, op2, diff1, diff2) => new Subtract(diff1, diff2));

const Multiply = createOp((a, b) => a * b, "*", 
	(a, op1, op2, diff1, diff2) => new Add(new Multiply(op1, diff2), new Multiply(diff1, op2)));

const Divide = createOp((a, b) => a / b, "/", 
	(a, op1, op2, diff1, diff2) => new Divide(new Subtract(new Multiply(diff1, op2), new Multiply(op1, diff2)), new Multiply(op2, op2)));

const Negate = createOp(a => -a, "negate", (a, op, diff) => new Negate(diff));

const Cube = createOp(a => Math.pow(a, 3), "cube", 
	(a, op, diff) => new Multiply(new Multiply(new Const(3), diff), new Multiply(op, op)));

const Cbrt = createOp(a => Math.pow(Math.abs(a), 1/3) * Math.sign(a), "cbrt", 
	(a, op, diff) => new Divide(diff, new Multiply(new Const(3), new Multiply(new Cbrt(op), new Cbrt(op)))));

const Sumsq = createOp(
	(...args) => {
		let res = 0;
		for (const i of args){
			res += i * i;
		}
		return res;
	},
	"sumsq", 
	(a, ...ops_n_diffs) => {
		let res = new Const(0);
		let opLen = ops_n_diffs.length/2;
		for (let i = 0; i < opLen; i++){
			res = new Add(res, new Multiply(new Const(2), new Multiply(ops_n_diffs[i], ops_n_diffs[opLen + i])));
		}
		return res;
	}
);

const Length = createOp(
	(...args) => {
		let res = 0;
		for (const i of args){
			res += i * i;
		}
		return Math.sqrt(res);
	},
	"length", 
	(a, ...ops_n_diffs) => {
		if (ops_n_diffs.length === 0){
			return new Const(0);
		}
		let res = new Const(0);
		let opLen = ops_n_diffs.length/2;
		let opers = [];
		for (let i = 0; i < opLen; i++){
			res = new Add(res, new Multiply(ops_n_diffs[i], ops_n_diffs[opLen + i]));
			opers.push(ops_n_diffs[i]);
		}
		return new Divide(res, new Length(...opers));
	}
);

const ops = {"+" : [Add, 2], "-" : [Subtract, 2], "*" : [Multiply, 2], "/" : [Divide, 2], "negate" : [Negate, 1],
			"cube" : [Cube, 1], "cbrt" : [Cbrt, 1], "sumsq" : [Sumsq, -1], "length" : [Length, -1]};

const parse = input => {
	let clearInp = input.trim();
	let arr = clearInp.split(/\s+/g);
	let res = [];
	for (const i of arr){
		if (vars.has(i)){
			res.push(new Variable(i));
		} else if (!isNaN(i)){
			res.push(new Const(+i));
		} else {
			if (i in ops){
				let temp = [];
				for (let j = 1; j <= ops[i][1]; j++){
					temp.unshift(res.pop())
				}
				res.push(new ops[i][0](...temp));
			}
		} 
	}
	return res.pop();
}

class ParsingError extends Error{
	constructor(mess, str, item, len, flag){
		if (flag === "post"){
			super(mess + " after item " + item + " in string " + str);
		} else {
			super(mess + " after item " + Math.max(0, len - item) + " in string " + str);
		}
		this.name = "ParsingError";
	}
}

class BracketsError extends ParsingError {
  constructor(str, item, len, flag){
	  super("Mistake in brackets",  str, item, len, flag);
	  this.name = "BracketsError";
  }
}

class OperationsError extends ParsingError {
  constructor(str, item, oper, len, flag){
	  super("Invalid use of operation \"" + oper + "\"", str, item, len, flag);
	  this.name = "OperationsError";
  }
}

class BadSymbolError extends ParsingError {
  constructor(str, item, len, flag){
	  super("Bad symbol",  str, item, len, flag);
	  this.name = "BadSymbolError";
  }
}

class MissingOperationError extends ParsingError {
  constructor(str, item, len, flag){
	  super("Missing operation",  str, item, len, flag);
	  this.name = "MissingOperationError";
  }
}

class ExcessiveInformationError extends ParsingError {
  constructor(str, item, len, flag){
	  super("Extra symbols",  str, item, len, flag);
	  this.name = "ExcessiveInformationError";
  }
}

class EmptyInputError extends Error {
  constructor(){
	  super("Empty input");
	  this.name = "EmptyInputError";
  }
}

const parseElement = (arr, i, inp, flag) => {
	if (flag !== "pre" && flag !== "post"){
		throw new WrongFlagError();
	}
	if (vars.has(arr[i])){
		return [new Variable(arr[i]), i + 1];
	} else if (!isNaN(arr[i])){
		return [new Const(+arr[i]), i + 1];
	} else {
		if (arr[i] === "["){
			let temp = [];
			i++;
			while(!(arr[i] in ops)){	
				let pair = parseElement(arr, i, inp, flag);
				if (flag === "post"){
					temp.push(pair[0]);
				} else {
					temp.unshift(pair[0]);
				}
				i = pair[1];
				if (i >= arr.length){
					throw new BracketsError(inp, i, arr.length, flag);
				}
			}
			let thisOp = arr[i];
			if (ops[thisOp][1] != -1 && ops[thisOp][1] != temp.length){
				throw new OperationsError(inp, i, arr[i], arr.length, flag);
			}
			i++;
			if (arr[i] != "]"){
				throw new BracketsError(inp, i, arr.length, flag); 
			}
			return [new ops[thisOp][0](...temp), i + 1];
		} else {
			if (arr[i] === "]"){
				throw new MissingOperationError(inp, i, arr.length, flag);
			} else {
				throw new BadSymbolError(inp, i, arr.length, flag);
			}
		}
	}
}

class WrongFlagError extends Error{
	constructor(){
	  super("Wrong flag: flag should be \"pre\" or \"post\" ");
	  this.name = "WrongFlagError";
  }
}

const parseAbs = flag => input => {
	if (flag !== "pre" && flag !== "post"){
		throw new WrongFlagError();
	}
	let brackOp;
	let brackCl;
	if (flag === "post"){
		brackOp = " [ ";
		brackCl = " ] ";
	} else {
		brackOp = " ] ";
		brackCl = " [ ";
	}
	let clearInp = input.replaceAll("(", brackOp).replaceAll(")", brackCl).trim();
	let arr = clearInp.split(/\s+/g);
	if (flag === "pre"){
		arr = arr.reverse();
	}
	if (arr[0] === ""){
		throw new EmptyInputError();
	}
	
	let pair = parseElement(arr, 0, input, flag);
	if (pair[1] === arr.length){
		return pair[0];
	} else {
		throw new ExcessiveInformationError(input, pair[1], arr.length, flag);
	}
}

const parsePrefix = parseAbs("pre");

const parsePostfix = parseAbs("post");