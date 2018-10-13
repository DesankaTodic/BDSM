package rs.ac.uns.ftn.informatika.udd.lucene.search;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.DocumentHandler;
import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.PDFHandler;
import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.TextDocHandler;
import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.Word2007Handler;
import rs.ac.uns.ftn.informatika.udd.lucene.indexing.handlers.WordHandler;
import rs.ac.uns.ftn.informatika.udd.lucene.model.IndexUnit;
import rs.ac.uns.ftn.informatika.udd.lucene.model.RequiredHighlight;
import rs.ac.uns.ftn.informatika.udd.model.Book;
import rs.ac.uns.ftn.informatika.udd.repository.IBookRepository;

@Service
public class ResultRetriever {

	private ElasticsearchTemplate template;
	private IBookRepository bookRepository;

	@Autowired
	public ResultRetriever(ElasticsearchTemplate template, IBookRepository bookRepository) {
		this.template = template;
		this.bookRepository = bookRepository;
	}

	public List<Book> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		/*
		 * if (query == null) { return null; }
		 * 
		 * List<Book> results = new ArrayList<Book>();
		 * 
		 * SearchQuery searchQuery = new NativeSearchQueryBuilder() .withQuery(query)
		 * .build();
		 * 
		 * List<IndexUnit> indexUnits = template.queryForList(searchQuery,
		 * IndexUnit.class);
		 * 
		 * for (IndexUnit indexUnit : indexUnits) { Book book =
		 * bookRepository.findOne(indexUnit.getInternalId()); results.add(book); }
		 * 
		 * mapHighlightedContent(searchQuery, results); return results;
		 */
		if (query == null) {
			return null;
		}

		List<Book> results = new ArrayList<Book>();

		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(query);

		if (requiredHighlights.get(0).getFieldName().equals("text"))
			nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("text"));

		SearchQuery searchQuery = nativeSearchQueryBuilder.build();

		List<IndexUnit> indexUnits = template.queryForList(searchQuery, IndexUnit.class);

		for (IndexUnit indexUnit : indexUnits) {

			Book result = bookRepository.findOne(indexUnit.getInternalId());
			if (result != null) {
				result.setHighlight("");
				results.add(result);
			}
		}
		if (requiredHighlights.get(0).getFieldName().equals("text") && results.size() != 0) {
			mapHighlightedContent(searchQuery, results);
		}

		return results;
	}

	protected DocumentHandler getHandler(String fileName) {
		if (fileName.endsWith(".pdf")) {
			return new PDFHandler();
		} else {
			return null;
		}
	}

	private void mapHighlightedContent(SearchQuery searchQuery, final List<Book> books) {
		template.queryForPage(searchQuery, Book.class, new SearchResultMapper() {

			public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
				for (int i = 0; i < searchResponse.getHits().totalHits(); i++) {
					if (searchResponse.getHits().getHits().length <= 0) {
						return null;
					}
					String highlight = "";
					if (searchResponse.getHits().getAt(i).getHighlightFields() != null) {
						for (Text s : searchResponse.getHits().getAt(i).getHighlightFields().get("text")
								.getFragments()) {
							highlight += s.string();
						}
					}
					books.get(i).setHighlight(highlight);
				}
				return null;
			}
		});
	}
}
