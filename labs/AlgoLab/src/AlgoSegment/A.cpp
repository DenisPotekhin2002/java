#include <iostream>

using namespace std;
#include <math.h>

void set(long a[], int i, int v, int x, int lx, int rx){
    if (rx - lx == 1){
        a[x] = v;
    } else {
        int m = (lx + rx) / 2;
        if (i < m){
            set(a, i, v, 2 * x + 1, lx, m);
        } else {
            set(a, i, v, 2 * x + 2, m, rx);
        }
        a[x] = a[2 * x + 1] + a[2 * x + 2];
    }
}

long sum(long a[], int n, int lx, int rx, int l, int r){
    if (rx < l || lx > r){
        return 0;
    }
    if (lx >= l && rx <= r){
        return a[n];
    }
    int m = (lx + rx) / 2;
    long s1 = sum(a, 2 * n + 1, lx, m, l, r);
    long s2 = sum(a, 2 * n + 2, m + 1, rx, l, r);
    return s1 + s2;
}

int main(){
    int n;
    int m;
    scanf("%i %i", &n, &m);
    int len = (int)pow(2, (int)(log(n * 2 - 1)/log(2)));
    long a[2 * len - 1];
    for (int i = 0; i < 2 * len - 1; i++){
        a[i] = 0;
    }
    for (int i = 0; i < n; i++){
        int t;
        cin >> t;
        set(a, i, t, 0, 0, len);
    }
    for (int i = 0; i < m; i++){
        int t, t1, t2;
        scanf("%i %i %i", &t, &t1, &t2);
        if (t == 1){
            set(a, t1, t2, 0, 0, len);
        } else {
            printf("%ld\n", sum(a, 0, 0, len - 1, t1, t2 - 1));
        }
    }
    return 0;
}
