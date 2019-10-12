package algorithm;

public class FibonacciNumber {
    /*
    Dynamic programming
     */


    public static void main(String[] args) {
        int n = 8;


        long startTime = System.currentTimeMillis();
        System.out.println(fibonacci(30));
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);


        startTime = System.currentTimeMillis();
        System.out.println(fib(30));
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    /*recursive version
    Martin Fowler:Never hire a developer who computes the factorial using Recursion
     */
    static int fib(int n) {
        if (n <= 1) {
            return n;
        }

        return fib(n - 1) + fib(n - 2);
    }


    /**
     * convert recursion into for loop.Why do i spend so much time
     * converting.The essence of this kind of recursion is that After we calculate
     * the bottom,the result is not used anymore.It's used only once.
     */
    static int fibonacci(int n) {
        int f0 = 0;
        int f1 = 1;
        int f2 = 0;
        if (n <= 1) {
            return n;
        }

        for (int i = 2; i <= n; i++) {
            f2 = f0 + f1;//recursion row
            f0 = f1;//prepare for next callstack
            f1 = f2;
        }
        return f2;
    }
}
