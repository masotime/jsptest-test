
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;

import net.sf.jsptest.HtmlTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class JSPStringInputTest extends HtmlTestCase {

	private String jspFile;
	private File f;
	
	@Before
	public void setUp() throws Exception {
		super.setUp(); // important!
		
		jspFile = "<HTML><HEAD><TITLE>Hello Pineapples</TITLE>";
		jspFile += "</HEAD><BODY><H1>Hello World</H1>";
		jspFile += "<TABLE><TR><TD><P>This is an <B>embedded</B> table</P></TD></TR></TABLE>";
		jspFile += "Today is: <%= new java.util.Date().toString() %></BODY></HTML>";
		
		// have to create a real file in the filesystem :(
		f = new File("index.jsp");
		f.createNewFile();
		Writer w = new FileWriter(f);
		w.write(jspFile);
		w.close();
	}

	@After
	public void tearDown() throws Exception {
		// delete the temporary file
		f.delete();
		
		super.tearDown(); // important!
	}
	
	@Test
	public void testIndexJsp() throws Exception {
		get("/index.jsp");
		String expectedDate = new Date().toString();
		System.out.println(getRenderedResponse());
		
		page().shouldHaveTitle("Hello Pineapples");
		page().shouldContain("Today is: "+expectedDate);
		page().shouldContainElement("//TABLE/TR/TD/P/B");
		element("//TABLE/TR/TD/P/B").shouldContain("embedded");		
	}

}