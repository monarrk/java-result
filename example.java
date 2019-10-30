import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

/// Emulation of Rust's Result type
/// Useful for catching and handling runtime errors
/// Takes a var with a generic type and stores it along with an Err or Ok value
/// This way, you can return a Result with an Ok or Err value wrapped with the "real" value to give you some sick nasty error handling
class Result<T> {
  // type Ok or Err
  private Results type;
  // wrapped data
  private T data;
  // type of data
  private String t;

  /// debugging functions
  public boolean is_err() {
    return this.type == Results.Err;
  }
  public boolean is_ok() {
    return this.type == Results.Ok;
  }
  public Results get_result() {
    return this.type;
  }
  public String get_type() {
    return this.t;
  }
  // please don't use these
  public void set_data(T a) {
    this.data = a;
  }
  public void set_result(Results a) {
    this.type = a;
  }

  /// unwrap a value. Very primitive; this.expect() should be used for real handling
  public T unwrap() {
    if (is_err()) {
      System.err.println("Got err value");
      System.exit(1);
    }
    return this.data;
  }

  /// Unwrap a value and print a message
  public T expect(String msg) {
    if (is_err()) {
      System.err.printf("Got err value\n{ \"%s\" }\n", msg);
      System.exit(1);
    }
    return this.data;
  }

  public String toString() {
    return "{\n\tresult: " + this.type + "\n\tdata: " + this.data + "\n\ttype: " + this.t + "\n}";
  }

  public Result () {
    this.type = Results.Ok;
    this.data = null;
    this.t = this.data.getClass().getName();
  }

  public Result (T stuff, Results res) {
    this.type = res;
    this.data = stuff;
    this.t = this.data.getClass().getName();
  }
}

enum Results {
  Err,
  Ok,
}

class example {
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
    // We can handle errors with a bit more verbosity if we feel like it...
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
