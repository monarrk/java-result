import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

class Main {
  public static Result<String> read_file(String path) {
    String st;
    try {
      File fake_file = new File(path);
      BufferedReader br = new BufferedReader(new FileReader(fake_file));
      
      // Oh no! 
      while ((st = br.readLine()) != null) 
        System.out.println(st);  
    } catch (Exception e) {
      return new Result<String>("", Results.Err);
    }
    return new Result<String>(st, Results.Ok);
  }

  public static void main(String[] args) {
    Result something = new Result<String>("Hello", Results.Ok);

    // We can unwrap the underlying value easily
    System.out.println(something.unwrap());
    System.out.println(something.toString());

    // Impossible to predict if we'll get an exception at runtime...let's handle it with a result
    // We can handle errors verbosely and get full control to avoid panics...
    String res2;
    Result<String> file = read_file("this_does_not_exist.txt");
    switch (file.get_result()) {
      case Ok:
      res2 = file.unwrap();
      break;

      case Err:
      System.out.printf("Failed to read file; skipping\n");
      break;
    }

    // ...or we can be a little bit lazier
    // expect prints a debug message if it panics
    String res = read_file("this_does_not_exist.txt").expect("Failed to read file...");
    System.out.println(res);
  }
}
