package in.theinsanetechie.reactivebackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Quote {
    private String id;
    private String book;
    private String content;

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", book='" + book + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (id != null ? !id.equals(quote.id) : quote.id != null) return false;
        if (book != null ? !book.equals(quote.book) : quote.book != null) return false;
        return content != null ? content.equals(quote.content) : quote.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
