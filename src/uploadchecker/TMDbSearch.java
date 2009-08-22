package uploadchecker;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TMDbSearch {
		
    public static List<SearchResult> search(String query) throws HttpException,
        IOException, SAXException, ParserConfigurationException,
        XPathExpressionException {
        query = query.replaceAll(" ", "+");
        String request = "http://api.themoviedb.org/2.1/Movie.search/en/xml/" +
                "d4ad46ee51d364386b6cf3b580fb5d8c/" + query;
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(request);

        // Send GET request
        int statusCode = client.executeMethod(method);

        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + method.getStatusLine());
        }
        InputStream rstream = null;

        // Get the response body
        rstream = method.getResponseBodyAsStream();

        // Process response
        Document response = DocumentBuilderFactory.newInstance().
                                    newDocumentBuilder().parse(rstream);

        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        //Get all search Result nodes
        NodeList nodes = 
            (NodeList) xPath.evaluate("/OpenSearchDescription/movies/movie",
                                        response, XPathConstants.NODESET);
        int nodeCount = nodes.getLength();

        List<SearchResult> l = new ArrayList<SearchResult>();

        //iterate over search Result nodes
        for (int i = 0; i < nodeCount; i++) {
            Node n = nodes.item(i);
            String name = (String) xPath.evaluate("name", n,
                                                    XPathConstants.STRING);
            if (name == null || name.length() <= 0) {
                continue;
            }
            //System.out.println(name);
            String id = (String) xPath.evaluate("id", n,
                                                    XPathConstants.STRING);
            String imdbID = (String) xPath.evaluate("imdb_id", n,
                                                    XPathConstants.STRING);
            //URL url = null;
            URL url = new URL((String) xPath.evaluate("url", n,
                                                    XPathConstants.STRING));
            NodeList imgNodes = (NodeList) xPath.evaluate("images/image", n,
                                                    XPathConstants.NODESET);
            String imgURL = null;
            for (int j = 0; j < imgNodes.getLength(); j++) {
                NamedNodeMap map = imgNodes.item(j).getAttributes();
                imgURL = map.getNamedItem("url").getTextContent();
                String type = map.getNamedItem("type").getTextContent();
                String size = map.getNamedItem("size").getTextContent();
                if (type.compareTo("poster") == 0
                        && size.compareTo("mid") == 0) {
                    break;
                }
            }
            l.add(new SearchResult(name, id,
                    imgURL == null ? null : new URL(imgURL), url, imdbID));
        }

        return l;
    }

    public static List<String> getOriginalLanguages(SearchResult sr)
            throws IOException {
        //PARSE sr.url if not found parse sr.imdbID
        //URL url = new URL("http://www.themoviedb.org/movie/1858");
        Source source = new Source(sr.url);
        source.setLogger(null);
        List<StartTag> allStartTags =
            source.getAllStartTags("div class=\"fact\"");
        List<String> res = new LinkedList<String>();
        for (StartTag s : allStartTags) {
            if (s == null) continue;
            Element e = s.getElement();
            if (e == null) continue;
            List<Element> childs = e.getChildElements();
            if (childs == null) continue;
            if (childs.size() < 2 || childs.size() > 2) continue;
            Element c1 = childs.get(0);
            if (c1 == null) continue;
            Segment s1 = c1.getContent();
            if (s1 == null) continue;
            String a = s1.toString();
            if (a == null) continue;
            if (a.compareTo("Languages (original):") != 0) continue;
            Element c2 = childs.get(1);
            if (c2 == null) continue;
            Segment s2 = c2.getContent();
            if (s2 == null) continue;
            String b = s2.toString();
            if (b == null) continue;
            b = b.trim();
            StringTokenizer st = new StringTokenizer(b, ".,|- ");
            while (st.hasMoreTokens()) res.add(st.nextToken());
            if (!res.isEmpty()) return res;
        }
        //NOT FOUND BECAUSE NOT RETURNED
        //url = new URL("http://www.imdb.com/title/tt0418279/");
        URL url = new URL("http://www.imdb.com/title/" + sr.imdbID + "/");
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; " +
                "Windows NT 6.0; en-GB; rv:1.9.1.2) Gecko/20090729 " +
                "Firefox/3.5.2 (.NET CLR 3.5.30729)");
        source = new Source(conn.getInputStream());
        source.setLogger(null);
        allStartTags = source.getAllStartTags("div class=\"info\"");
        for (StartTag s : allStartTags) {
            if (s == null) continue;
            Element e = s.getElement();
            if (e == null) continue;
            List<Element> h5elems = e.getAllElements(HTMLElementName.H5);
            if (h5elems == null) continue;
            if (h5elems.size() != 1) continue;
            Element h5 = h5elems.get(0);
            if (h5 == null) continue;
            Segment ch5 = h5.getContent();
            if (ch5 == null) continue;
            String sch5 = ch5.toString();
            if (sch5 == null) continue;
            if (sch5.compareTo("Language:") != 0) continue;
            List<Element> childs = e.getChildElements();
            if (childs.size() <= 1) continue;
            for (int c = 1; c < childs.size(); c++) {
                Element child = childs.get(c);
                if (child == null) continue;
                Segment content = child.getContent();
                if (content == null) continue;
                String lang = content.toString();
                if (lang == null) continue;
                res.add(lang.trim());
            }
            return res;
        }
        return new ArrayList<String>();
    }


    public static void main(String[] args) throws HttpException, 
        XPathExpressionException, IOException, SAXException,
        ParserConfigurationException {
        //System.out.println(getOriginalLanguage(null));
        List<SearchResult> l = search("transformers");
        System.out.println(l);
    }
}
