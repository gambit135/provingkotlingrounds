package Java8.lambdas;

@FunctionalInterface
public interface ExampleFunctionalInterface {
    void apply();
}

@FunctionalInterface
interface A {
    void methodA();
}

interface B extends A {

}

class MyClass {
    public static void main (String args[]){
        System.out.println("test");

        A a = () -> System.out.println("A");
        B b = () -> System.out.println("B");


    }
}
