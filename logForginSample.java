public class logForginSample {
  public void doNothing(HttpServletRequest request) {
      log.trace("Incoming request for: {}", request.getRequestURL());
  }
}
