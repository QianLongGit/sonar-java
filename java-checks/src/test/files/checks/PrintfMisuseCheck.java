import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Calendar;

class A {
  void foo(Calendar c){
    Object myObject;
    double value;
    String.format("The value of my integer is %d", "Hello World");
    String.format("First {0} and then {1}", "foo", "bar");  // Noncompliant  {{Looks like there is a confusion with the use of java.text.MessageFormat, parameters will be simply ignored here}}
    String.format("Duke's Birthday year is %tX", 12l);
    String.format("Display %3$d and then %d", 1, 2, 3);   // Noncompliant {{2nd argument is not used.}}
    String.format("Too many arguments %d and %d", 1, 2, 3);  // Noncompliant {{3rd argument is not used.}}
    String.format("Not enough arguments %d and %d", 1);
    String.format("First Line\n %d", 1); // Noncompliant {{%n should be used in place of \n to produce the platform-specific line separator.}}
    String.format("First Line");   // Noncompliant {{String contains no format specifiers.}}
    String.format("First Line%%"); // Noncompliant {{String contains no format specifiers.}}
    String.format("First Line%n"); // Compliant
    String.format("%< is equals to %d", 2);
    String.format("Is myObject null ? %b", myObject);   // Noncompliant {{Directly inject the boolean value.}}
    String.format("value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    String.format("string without arguments"); // Noncompliant {{String contains no format specifiers.}}

    PrintWriter pr;
    PrintStream ps;
    Formatter formatter;
    Locale loc;

    pr.format("The value of my integer is %d", "Hello World");
    pr.printf("The value of my integer is %d", "Hello World");
    ps.format("The value of my integer is %d", "Hello World");
    ps.printf(loc, "The value of my integer is %d", "Hello World");
    formatter.format("The value of my integer is %d", "Hello World");
    pr.format("%s:\tintCompact %d\tintVal %d\tscale %d\tprecision %d%n","", 1, 1, 1, 1);
    pr.format("%s:\tintCompact %n%n%n%d\tintVal %d\tscale %d\tprecision %d%n","", 1, 1, 1, 1);
    pr.format("%TH", 1l);
    pr.format("%d", new Long(12));
    pr.format("%d", new java.math.BigInteger("12"));
    String.format("Too many arguments %d and %d and %d", 1, 2, 3, 4);  // Noncompliant {{4th argument is not used.}}
    String.format("normal %d%% ", 1);  //Compliant
    String.format("Duke's Birthday year is %t", 12l);
    String.format("Duke's Birthday year is %tH", 12l);  // Compliant
    String.format("Duke's Birthday year is %tH", Long.valueOf(12L));  // Compliant
    String.format("Duke's Birthday year is %tH", loc);
    String.format("%08d%n", 1);
    GregorianCalendar gc;
    String.format("Duke's Birthday year is %tH", gc);
    String.format("Duke's Birthday year is %t", loc);
    String.format("Accessed before %tF%n", java.time.LocalDate.now()); // Compliant
    System.out.printf("%1$ty_%1$tm_%1$td_%1$tH_%1$tM_%1$tS", java.time.LocalDateTime.now()); // Compliant

    pr.format("string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    pr.format(loc, "string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    pr.printf("string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    pr.printf(loc, "string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    ps.format("string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    ps.format(loc, "string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    ps.printf("string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    ps.printf(loc, "string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    formatter.format("string without arguments"); // Noncompliant  {{String contains no format specifiers.}}
    formatter.format(loc, "string without arguments"); // Noncompliant  {{String contains no format specifiers.}}

    pr.format("value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    pr.format(loc, "value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    pr.printf("value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    pr.printf(loc, "value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    ps.format("value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    ps.format(loc, "value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    ps.printf("value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    ps.printf(loc, "value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    formatter.format("value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    formatter.format(loc, "value is " + value); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}

    pr.format("value is "+"asd"); // Noncompliant {{Format specifiers should be used instead of string concatenation.}}
    pr.format("value is "+
        "asd"); // Compliant operand not on the same line.
    String.format("value is %d", value); // Compliant

    String.format("%0$s", "tmp");

    String.format("Dude's Birthday: %1$tm %<te,%<tY", c); // Compliant
    String.format("Dude's Birthday: %1$tm %1$te,%1$tY", c); // Compliant
    String.format("log/protocol_%tY_%<tm_%<td_%<tH_%<tM_%<tS.zip", new java.util.Date());

    MessageFormat messageFormat = new MessageFormat("{0}");
    messageFormat.format(new Object(), new StringBuffer(), new FieldPosition(0)); // Compliant - Not considered
    messageFormat.format(new Object()); // Compliant - Not considered
    messageFormat.format("");  // Compliant - Not considered

    Object[] objs;
    MessageFormat.format("{0,number,$'#',##}", value); // Compliant
    MessageFormat.format("Result ''{0}''.", 14); // Compliant
    MessageFormat.format("Result '{0}'", 14); // Noncompliant {{String contains no format specifiers.}}
    MessageFormat.format("Result ' {0}", 14);
    MessageFormat.format("Result {{{0}}.", 14);
    MessageFormat.format("Result {0}!", myObject.toString()); // Noncompliant {{No need to call toString "method()" as formatting and string conversion is done by the Formatter.}}
    MessageFormat.format("Result {0}!", myObject.hashCode()); // Compliant
    MessageFormat.format("Result yeah!", 14); // Noncompliant {{String contains no format specifiers.}}
    MessageFormat.format("Result {1}!", 14);
    MessageFormat.format("Result {0} and {1}!", 14);
    MessageFormat.format("Result {0} and {0}!", 14, 42); // Noncompliant {{2nd argument is not used.}}
    MessageFormat.format("Result {0, number, integer} and {1, number, integer}!", 14, 42); // compliant
    MessageFormat.format("Result {0} and {1}!", 14, 42, 128); // Noncompliant {{3rd argument is not used.}}
    MessageFormat.format("{0,number,#.#}{1}", new Object[] {0.07, "$"}); // Compliant
    MessageFormat.format("{0,number,#.#}{1}", new Object[] {0.07});
    MessageFormat.format("{0,number,#.#}{1}", objs); // Compliant - skipped as the array is not initialized in the method invocation
    MessageFormat.format("{0,number,#.#}{1}", new Object[42]); // Compliant - Not considered
    MessageFormat.format("value=\"'{'{0}'}'{1}\"", new Object[] {"value 1", "value 2"});
    MessageFormat.format("value=\"{0}'{'{1}'}'\"", new Object[] {"value 1", "value 2"});

    java.util.logging.Logger logger;
    logger.log(java.util.logging.Level.SEVERE, "{0,number,$'#',##}", value); // Compliant
    logger.log(java.util.logging.Level.SEVERE, "Result ''{0}''.", 14); // Compliant
    logger.log(java.util.logging.Level.SEVERE, "Result '{0}'", 14); // Noncompliant {{String contains no format specifiers.}}
    logger.log(java.util.logging.Level.SEVERE, "Result ' {0}", 14);
    logger.log(java.util.logging.Level.SEVERE, "Result {{{0}}.", 14);
    logger.log(java.util.logging.Level.SEVERE, "Result {0}!", myObject.toString()); // Noncompliant {{No need to call toString "method()" as formatting and string conversion is done by the Formatter.}}
    logger.log(java.util.logging.Level.SEVERE, "Result {0}!", myObject.hashCode()); // Compliant
    logger.log(java.util.logging.Level.SEVERE, "Result yeah!", 14); // Noncompliant {{String contains no format specifiers.}}
    logger.log(java.util.logging.Level.SEVERE, "Result yeah!", new Exception()); // compliant, throwable parameter
    logger.log(java.util.logging.Level.SEVERE, "Result {1}!", 14);
    logger.log(java.util.logging.Level.SEVERE, "Result {0} and {1}!", 14);
    logger.log(java.util.logging.Level.SEVERE, "{0,number,#.#}{1}", new Object[] {0.07, "$"}); // Compliant
    logger.log(java.util.logging.Level.SEVERE, "{0,number,#.#}{1}", new Object[] {0.07});
    logger.log(java.util.logging.Level.SEVERE, "{0,number,#.#}{1}", objs); // Compliant - skipped as the array is not initialized in the method invocation
    logger.log(java.util.logging.Level.SEVERE, "{0,number,#.#}{1}", new Object[42]); // Compliant - Not considered
    logger.log(java.util.logging.Level.SEVERE, "value=\"'{'{0}'}'{1}\"", new Object[] {"value 1", "value 2"});
    logger.log(java.util.logging.Level.SEVERE, "value=\"{0}'{'{1}'}'\"", new Object[] {"value 1", "value 2"});

    org.slf4j.Logger slf4jLog;
    org.slf4j.Marker marker;

    slf4jLog.debug(marker, "message {}");
    slf4jLog.debug(marker, "message ", 1); // Noncompliant {{String contains no format specifiers.}}
    slf4jLog.debug(marker, "message {}", 1);
    slf4jLog.debug(marker, "message {} - {}", 1, 2);
    slf4jLog.debug(marker, "message {}", 1, 2);// Noncompliant {{2nd argument is not used.}}
    slf4jLog.debug(marker, "message {} {} {}", 1, 2, 3);
    slf4jLog.debug(marker, "message {} {}", 1, 2, 3); // Noncompliant
    slf4jLog.debug(marker, "message {} {}", new Object[]{1, 2, 3}); // Noncompliant
    slf4jLog.debug(marker, "message {} {} {}", new Object[]{1, 2, 3}); // compliant
    slf4jLog.debug(marker, "message ", new Exception());
    slf4jLog.debug(marker, "message {}", new Exception());


    slf4jLog.debug("message {}");
    slf4jLog.debug("message ", 1); // Noncompliant {{String contains no format specifiers.}}
    slf4jLog.debug("message {}", 1);
    slf4jLog.debug("message {} - {}", 1, 2);
    slf4jLog.debug("message {}", 1, 2);// Noncompliant {{2nd argument is not used.}}
    slf4jLog.debug("message {} {} {}", 1, 2, 3);
    slf4jLog.debug("message {} {}", 1, 2, 3); // Noncompliant
    slf4jLog.debug("message {} {}", new Object[]{1, 2, 3}); // Noncompliant
    slf4jLog.debug("message {} {} {}", new Object[]{1, 2, 3}); // compliant
    slf4jLog.debug("message ", new Exception());
    slf4jLog.debug("message {}", new Exception());

    slf4jLog.error("message {}");
    slf4jLog.error("message ", 1); // Noncompliant {{String contains no format specifiers.}}
    slf4jLog.error("message {}", 1);
    slf4jLog.info("message {} - {}", 1, 2);
    slf4jLog.info("message {}", 1, 2);// Noncompliant {{2nd argument is not used.}}
    slf4jLog.info("message {} {} {}", 1, 2, 3);
    slf4jLog.trace("message {} {}", 1, 2, 3); // Noncompliant
    slf4jLog.trace("message {} {}", new Object[]{1, 2, 3}); // Noncompliant
    slf4jLog.trace("message {} {} {}", new Object[]{1, 2, 3}); // compliant
    slf4jLog.trace("message ", new Exception());
    slf4jLog.trace("message {}", new Exception());
    slf4jLog.warn("message {}");
    slf4jLog.warn("message ", 1); // Noncompliant {{String contains no format specifiers.}}
    slf4jLog.warn("message {}", 1);
    slf4jLog.warn("message");

  }
}
