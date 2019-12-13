import org.graalvm.polyglot.Context;

public class HelloPolyglot {
    public static void main(String[] args) {
        System.out.println("Hello Java!");
        try (Context context = Context.create()) {
            context.eval("python", "print('Hello Python!')");
        }
    }
}
