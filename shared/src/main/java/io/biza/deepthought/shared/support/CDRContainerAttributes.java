package io.biza.deepthought.shared.support;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.biza.babelfish.cdr.models.payloads.LinksPaginatedV1;
import io.biza.babelfish.cdr.models.payloads.LinksV1;
import io.biza.babelfish.cdr.models.payloads.MetaPaginatedV1;
import io.biza.babelfish.cdr.models.payloads.MetaV1;

public class CDRContainerAttributes {
  
  public static MetaPaginatedV1 toMetaPaginated(Page<?> inputPage) {
    return MetaPaginatedV1.builder().totalPages(inputPage.getTotalPages())
        .totalRecords(Long.valueOf(inputPage.getTotalElements()).intValue()).build();
  }

  public static MetaV1 toMeta() {
    return new MetaV1();
  }

  public static LinksV1 toLinks() {
    LinksV1 links = new LinksV1();
    ServletUriComponentsBuilder uriComponents = ServletUriComponentsBuilder.fromCurrentRequest();
    links.self(uriComponents.build().toUri());
    return links;
  }

  public static LinksPaginatedV1 toLinksPaginated(Page<?> inputPage) {
    LinksPaginatedV1 links = LinksPaginatedV1.builder().build();
    ServletUriComponentsBuilder uriComponents = ServletUriComponentsBuilder.fromCurrentRequest();

    links.self(uriComponents.build().toUri());

    if (!inputPage.isFirst()) {
      links.first(uriComponents.replaceQueryParam("page", 1)
          .replaceQueryParam("page-size", inputPage.getNumberOfElements()).build().toUri());
    }

    if (!inputPage.isLast()) {
      links.last(uriComponents.replaceQueryParam("page", inputPage.getTotalPages() + 1)
          .replaceQueryParam("page-size", inputPage.getNumberOfElements()).build().toUri());
    }

    if (inputPage.hasPrevious()) {
      links.prev(
          uriComponents.replaceQueryParam("page", inputPage.previousPageable().getPageNumber() + 1)
              .replaceQueryParam("page-size", inputPage.getNumberOfElements()).build().toUri());
    }

    if (inputPage.hasNext()) {
      links.next(
          uriComponents.replaceQueryParam("page", inputPage.nextPageable().getPageNumber() + 1)
              .replaceQueryParam("page-size", inputPage.getNumberOfElements()).build().toUri());
    }

    return links;
  }

}
