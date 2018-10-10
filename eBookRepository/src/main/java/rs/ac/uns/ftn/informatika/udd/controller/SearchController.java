package rs.ac.uns.ftn.informatika.udd.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.udd.lucene.model.AdvancedQuery;
import rs.ac.uns.ftn.informatika.udd.lucene.model.RequiredHighlight;
import rs.ac.uns.ftn.informatika.udd.lucene.model.SearchType;
import rs.ac.uns.ftn.informatika.udd.lucene.model.SimpleQuery;
import rs.ac.uns.ftn.informatika.udd.lucene.search.QueryBuilder;
import rs.ac.uns.ftn.informatika.udd.lucene.search.ResultRetriever;
import rs.ac.uns.ftn.informatika.udd.model.Book;

@RestController
public class SearchController {

		@Autowired
		private ResultRetriever resultRetriever;
	
		@PostMapping(value="/search/term", consumes="application/json")
		public ResponseEntity<List<Book>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {		
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<Book> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/search/fuzzy", consumes="application/json")
		public ResponseEntity<List<Book>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<Book> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/search/prefix", consumes="application/json")
		public ResponseEntity<List<Book>> searchPrefix(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.prefix, simpleQuery.getField(), simpleQuery.getValue());
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<Book> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/search/range", consumes="application/json")
		public ResponseEntity<List<Book>> searchRange(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.range, simpleQuery.getField(), simpleQuery.getValue());
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<Book> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/search/phrase", consumes="application/json")
		public ResponseEntity<List<Book>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<Book> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/search/boolean", consumes="application/json")
		public ResponseEntity<List<Book>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
			org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());
			
			BoolQueryBuilder builder = QueryBuilders.boolQuery();
			if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
				builder.must(query1);
				builder.must(query2);
			}else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
				builder.should(query1);
				builder.should(query2);
			}else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
				builder.must(query1);
				builder.mustNot(query2);
			}
			
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
			rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
			List<Book> results = resultRetriever.getResults(builder, rh);			
			return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/search/queryParser", consumes="application/json")
		public ResponseEntity<List<Book>> search(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query=QueryBuilders.queryStringQuery(simpleQuery.getValue());			
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			List<Book> results = resultRetriever.getResults(query, rh);
			return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
		}
	
		
	
}
