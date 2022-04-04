"use strict";

const vars = new Map([["x", 0], ["y", 1], ["z", 2]]);

const cnst = value => _ => value;
const variable = a => (...args) => args[vars.get(a)];

const abstractOp = a => {
	let oper = (...ops) => (...args) => {
		let temp = [];
		for (const i of ops){
			temp.push(i(...args));
		}
		return a(...temp);
	};
	oper.arity = a.length;
	return oper;
}

const add = abstractOp((a, b) => a + b);

const subtract = abstractOp((a, b) => a - b);

const multiply = abstractOp((a, b) => a * b);

const divide = abstractOp((a, b) => a / b);

const negate = abstractOp(a => -a);

const one = cnst(1);

const two = cnst(2);

const min5 = abstractOp((a, b, c, d, e) => Math.min(a, b, c, d, e));

const max3 = abstractOp((a, b, c) => Math.max(a, b, c));

const operations = new Map([["+", add], ["-", subtract], ["*", multiply], ["/", divide], 
		["negate", negate], ["max3", max3], ["min5", min5]]);

const cnsts = new Map([["one", one], ["two", two]]);

const parse = input => {
	let clearInp = input.trim();
	let arr = clearInp.split(/\s+/g);
	let res = [];
	for (const i of arr){
		if (vars.has(i)){
			res.push(variable(i));
		} else if (!isNaN(i)){
			res.push(cnst(+i));
		} else if (cnsts.has(i)){
			res.push(cnsts.get(i));
		} else if (operations.has(i)){
			let op = operations.get(i);
			let temp = [];
			for (let j = 1; j <= op.arity; j++){
				temp.unshift(res.pop())
			}
			res.push(op(...temp));
		} else {
			return "Invalid operation";
		}
	}
	return res.pop();
}

const testStr = add(subtract(multiply(variable("x"), variable("x")), multiply(cnst(2), variable("x"))), cnst(1));
const test = () => {
	let print = [];
	for (let i = 0; i <= 10; i++){
		print.push(testStr(i));
	}
	return print;
}