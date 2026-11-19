# How Spring Boot Maps a Thymeleaf Template to a Controller

Spring Boot maps a Thymeleaf template to a controller through the URL path, not through any direct template-to-controller reference.

## The request flow

1. A browser sends a request to a URL, e.g. `GET /products`.
2. Spring's `DispatcherServlet` matches that URL to a `@Controller` method via `@GetMapping`, `@RequestMapping`, etc.
3. The controller method adds data to the `Model` and returns a **string** — the template name.
4. Spring's `ViewResolver` (configured for Thymeleaf by default in Spring Boot) resolves that name to a file: `src/main/resources/templates/<name>.html`.
5. Thymeleaf renders the template with the model data and sends the HTML back.

## Concrete example

```java
@Controller
public class ProductController {

    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/list";  // → templates/products/list.html
    }
}
```

The template `products/list.html` doesn't reference the controller at all. The connection is made purely by:

- The **URL** the browser requests (`/products`)
- The **return value** of the matched controller method (`"products/list"`)

## What the template knows

Nothing about controllers. A Thymeleaf template only knows about the model attributes the controller put in. If a template submits a form to `/products/save`, Spring again routes that POST to whatever controller method is mapped to that path.

## The view resolver default

Spring Boot auto-configures `ThymeleafViewResolver` with:

- **Prefix:** `classpath:/templates/`
- **Suffix:** `.html`

So returning `"products/list"` resolves to `classpath:/templates/products/list.html`. You can override these in `application.properties` with `spring.thymeleaf.prefix` and `spring.thymeleaf.suffix`.
