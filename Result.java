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
