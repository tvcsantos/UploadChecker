package uploadchecker;

import java.io.Serializable;
import java.net.URL;

public class SearchResult implements Serializable {

    public String movie;
    public String movieID;
    public URL imgURL;
    public URL url;
    public String imdbID;

    public SearchResult(String movie, String movieID, URL imgURL,
            URL url, String imdbID) {
        this.movie = movie;
        this.movieID = movieID;
        this.imgURL = imgURL;
        this.url = url;
        this.imdbID = imdbID;
    }

    public String toExtendedString() {
        return "SearchResult[" + movie + ", " + movieID +
                ", " + imgURL + ", " + url + ", " + imdbID + "]";
    }

    @Override
    public String toString() {
        return movie;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((movieID == null) ? 0 : movieID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SearchResult other = (SearchResult) obj;
        if (movieID == null) {
            if (other.movieID != null) {
                return false;
            }
        } else if (!movieID.equals(other.movieID)) {
            return false;
        }
        return true;
    }
}
