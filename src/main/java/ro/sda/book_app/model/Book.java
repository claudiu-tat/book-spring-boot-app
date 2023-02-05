package ro.sda.book_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Title should not be empty!")        // here we validate de year interval
    @NotBlank(message = "Title should not be empty!")
    private String title;
    @NotNull(message = "Author should not be empty!")
    @NotBlank(message = "Author should not be empty!")
    private String author;
    @Min(value = 1500, message = "Year of publication should be after 1500!")
    @Max(value = 2023, message = "Year of publication should in the past, not in the future!")
    @Column(name = "publication_year")
    private int year;
}
