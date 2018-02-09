package recursion;
public class TailRecursion {

    public static void main(String[] args) {
        TailRecursion tailRecursion = new TailRecursion();
        System.out.println(tailRecursion.rFactorial(5));
    }

    public int rFactorial(int n){
        return tFactorial(n,1);
    }

    private int tFactorial(int n, int f) {
        if(n<=0){
            throw new RuntimeException("Boom !!!!");
        }
        return tFactorial(n-1,n*f);
    }
}

//https://dzone.com/articles/the-tale-of-tail-recursion
//Tail recursion optimazation possible in scala but not possible in java .