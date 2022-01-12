namespace rt{
    public static class Kata
{
  public static int Factorial(int n)
  {
     return n > 1 ? Factorial(--n) : n;
  }

}
}