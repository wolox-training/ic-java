package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wolox.training.models.BookInfo;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/search")
@Validated
@Api
public class IsbnSearchController {

  @Autowired
  private OpenLibraryService openLibraryService;

  @GetMapping("/{id}")
  @ApiOperation(value = "Find book info by ISBN")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of books"),
      @ApiResponse(code = 404, message = "Book not found"),
  })
  public BookInfo findAll(@RequestParam @NotNull String isbn) {
    return openLibraryService.bookInfo(isbn);
  }
}
