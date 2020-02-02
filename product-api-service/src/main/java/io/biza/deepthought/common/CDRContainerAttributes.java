package io.biza.deepthought.common;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.biza.babelfish.cdr.models.payloads.common.Links;
import io.biza.babelfish.cdr.models.payloads.common.LinksPaginated;
import io.biza.babelfish.cdr.models.payloads.common.Meta;
import io.biza.babelfish.cdr.models.payloads.common.MetaPaginated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CDRContainerAttributes {

  public static MetaPaginated toMetaPaginated(Page<?> inputPage) {
    return new MetaPaginated().totalPages(inputPage.getTotalPages())
        .totalRecords(Long.valueOf(inputPage.getTotalElements()).intValue());
  }

  public static Meta toMeta() {
    return new Meta();
  }

  public static Links toLinks() {
    Links links = new Links();
    ServletUriComponentsBuilder uriComponents = ServletUriComponentsBuilder.fromCurrentRequest();
    links.self(uriComponents.build().toUri());
    return links;
  }

  public static LinksPaginated toLinksPaginated(Page<?> inputPage) {
    LinksPaginated links = new LinksPaginated();
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
