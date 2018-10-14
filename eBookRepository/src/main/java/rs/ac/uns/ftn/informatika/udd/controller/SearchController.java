package rs.ac.uns.ftn.informatika.udd.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.udd.lucene.indexing.filters.CyrillicLatinConverter;
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

	@PostMapping(value = "/search/term", consumes = "application/json")
	// public ResponseEntity<List<Book>> searchTermQuery(@RequestBody SimpleQuery
	// simpleQuery) throws Exception {
	public ResponseEntity<Set<Book>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {

		String latin = toLowerCaseAndLatin(simpleQuery.getValue());
		org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(SearchType.regular,
				simpleQuery.getField(), latin);
		List<RequiredHighlight> rh1 = new ArrayList<RequiredHighlight>();
		rh1.add(new RequiredHighlight(simpleQuery.getField(), latin));
		List<Book> results1 = resultRetriever.getResults(query1, rh1);

		String cir = toLowerCaseAndCir(simpleQuery.getValue());
		org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(SearchType.regular,
				simpleQuery.getField(), cir);
		List<RequiredHighlight> rh2 = new ArrayList<RequiredHighlight>();
		rh2.add(new RequiredHighlight(simpleQuery.getField(), cir));
		List<Book> results2 = resultRetriever.getResults(query2, rh2);

		Set<Book> set = new HashSet<Book>();
		set.addAll(results1);
		set.addAll(results2);

		return new ResponseEntity<Set<Book>>(set, HttpStatus.OK);
	}

	@PostMapping(value = "/search/fuzzy", consumes = "application/json")
	public ResponseEntity<Set<Book>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {

			String latin = toLowerCaseAndLatin(simpleQuery.getValue()); 
			org.elasticsearch.index.query.QueryBuilder query1= QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), latin);
			List<RequiredHighlight> rh1 = new ArrayList<RequiredHighlight>();
			rh1.add(new RequiredHighlight(simpleQuery.getField(), latin));
			List<Book> results1 = resultRetriever.getResults(query1, rh1);	
			
			String cir = toLowerCaseAndCir(simpleQuery.getValue());
			org.elasticsearch.index.query.QueryBuilder query2= QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), cir);
			List<RequiredHighlight> rh2 = new ArrayList<RequiredHighlight>();
			rh2.add(new RequiredHighlight(simpleQuery.getField(), cir));
			List<Book> results2 = resultRetriever.getResults(query2, rh2);	
			
			Set<Book> set = new HashSet<Book>();
			set.addAll(results1);
			set.addAll(results2);
			
			return new ResponseEntity<Set<Book>>(set, HttpStatus.OK);
	}

	@PostMapping(value = "/search/prefix", consumes = "application/json")
	public ResponseEntity<List<Book>> searchPrefix(@RequestBody SimpleQuery simpleQuery) throws Exception {
		org.elasticsearch.index.query.QueryBuilder query = QueryBuilder.buildQuery(SearchType.prefix,
				simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<Book> results = resultRetriever.getResults(query, rh);
		return new ResponseEntity<List<Book>>(results, HttpStatus.OK);

	}

	@PostMapping(value = "/search/range", consumes = "application/json")
	public ResponseEntity<List<Book>> searchRange(@RequestBody SimpleQuery simpleQuery) throws Exception {
		org.elasticsearch.index.query.QueryBuilder query = QueryBuilder.buildQuery(SearchType.range,
				simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<Book> results = resultRetriever.getResults(query, rh);
		return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
	}

	@PostMapping(value = "/search/phrase", consumes = "application/json")
	public ResponseEntity<Set<Book>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {

			String latin = toLowerCaseAndLatin(simpleQuery.getValue()); 
			org.elasticsearch.index.query.QueryBuilder query1= QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), latin);
			List<RequiredHighlight> rh1 = new ArrayList<RequiredHighlight>();
			rh1.add(new RequiredHighlight(simpleQuery.getField(), latin));
			List<Book> results1 = resultRetriever.getResults(query1, rh1);	
			
			String cir = toLowerCaseAndCir(simpleQuery.getValue());
			org.elasticsearch.index.query.QueryBuilder query2= QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), cir);
			List<RequiredHighlight> rh2 = new ArrayList<RequiredHighlight>();
			rh2.add(new RequiredHighlight(simpleQuery.getField(), cir));
			List<Book> results2 = resultRetriever.getResults(query2, rh2);	
			
			Set<Book> set = new HashSet<Book>();
			set.addAll(results1);
			set.addAll(results2);
			
			return new ResponseEntity<Set<Book>>(set, HttpStatus.OK);
	}

	@PostMapping(value = "/search/boolean", consumes = "application/json")
	public ResponseEntity<Set<Book>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {

//		String latinField1 = toLowerCaseAndLatin(advancedQuery.getValue1());
//		String latinField2 = toLowerCaseAndLatin(advancedQuery.getValue2());
//
//		org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(SearchType.regular,
//				advancedQuery.getField1(), latinField1);
//		org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(SearchType.regular,
//				advancedQuery.getField2(), latinField2);
//
//		BoolQueryBuilder builder1 = QueryBuilders.boolQuery();
//
//		if (advancedQuery.getOperation().equalsIgnoreCase("AND")) {
//			builder1.must(query1);
//			builder1.must(query2);
//		} else if (advancedQuery.getOperation().equalsIgnoreCase("OR")) {
//			builder1.should(query1);
//			builder1.should(query2);
//		} else if (advancedQuery.getOperation().equalsIgnoreCase("NOT")) {
//			builder1.must(query1);
//			builder1.mustNot(query2);
//		}
//
//		List<RequiredHighlight> rh1 = new ArrayList<RequiredHighlight>();
//		rh1.add(new RequiredHighlight(advancedQuery.getField1(), latinField1));
//		rh1.add(new RequiredHighlight(advancedQuery.getField2(), latinField2));
//		List<Book> results1 = resultRetriever.getResults(builder1, rh1);
//
//		String cirField1 = toLowerCaseAndLatin(advancedQuery.getValue1());
//		String cirField2 = toLowerCaseAndLatin(advancedQuery.getValue2());
//
//		org.elasticsearch.index.query.QueryBuilder query3 = QueryBuilder.buildQuery(SearchType.regular,
//				advancedQuery.getField1(), cirField1);
//		org.elasticsearch.index.query.QueryBuilder query4 = QueryBuilder.buildQuery(SearchType.regular,
//				advancedQuery.getField2(), cirField2);
//
//		BoolQueryBuilder builder2 = QueryBuilders.boolQuery();
//
//		if (advancedQuery.getOperation().equalsIgnoreCase("AND")) {
//			builder2.must(query3);
//			builder2.must(query4);
//		} else if (advancedQuery.getOperation().equalsIgnoreCase("OR")) {
//			builder2.should(query3);
//			builder2.should(query4);
//		} else if (advancedQuery.getOperation().equalsIgnoreCase("NOT")) {
//			builder2.must(query3);
//			builder2.mustNot(query4);
//		}
//
//		List<RequiredHighlight> rh2 = new ArrayList<RequiredHighlight>();
//		rh2.add(new RequiredHighlight(advancedQuery.getField1(), cirField1));
//		rh2.add(new RequiredHighlight(advancedQuery.getField2(), cirField2));
//		List<Book> results2 = resultRetriever.getResults(builder2, rh2);
//
//		Set<Book> set = new HashSet<Book>();
//		set.addAll(results1);
//		set.addAll(results2);
//
//		return new ResponseEntity<Set<Book>>(set, HttpStatus.OK);

//			milica
			String latinField1 = toLowerCaseAndLatin(advancedQuery.getValue1()); 
			String latinField2 = toLowerCaseAndLatin(advancedQuery.getValue2());
			
			String cirField1 = toLowerCaseAndCir(advancedQuery.getValue1());
			String cirField2 = toLowerCaseAndCir(advancedQuery.getValue2());
			List<String> params1 = new ArrayList<String>();
			List<String> params2 = new ArrayList<String>();
			params1.add(latinField1);
			params1.add(cirField1);
			
			params2.add(cirField2);
			params2.add(latinField2);
			
			org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilders.termsQuery(advancedQuery.getField1(), params1);
			org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilders.termsQuery(advancedQuery.getField2(), params2);

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
			rh.add(new RequiredHighlight(advancedQuery.getField1(), latinField1));
			rh.add(new RequiredHighlight(advancedQuery.getField1(), latinField2));
			rh.add(new RequiredHighlight(advancedQuery.getField1(), cirField1));
			rh.add(new RequiredHighlight(advancedQuery.getField1(), cirField2));

			List<Book> results = resultRetriever.getResults(builder, rh);
			
			Set<Book> set = new HashSet<Book>();
			set.addAll(results);
			
			return new ResponseEntity<Set<Book>>(set, HttpStatus.OK);
	}

	@PostMapping(value = "/search/queryParser", consumes = "application/json")
	public ResponseEntity<List<Book>> search(@RequestBody SimpleQuery simpleQuery) throws Exception {
		org.elasticsearch.index.query.QueryBuilder query = QueryBuilders.queryStringQuery(simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		List<Book> results = resultRetriever.getResults(query, rh);
		return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
	}

	private String toLowerCaseAndLatin(String s) {
		String x = s.toLowerCase();
		x = CyrillicLatinConverter.cir2lat(x);
		return x;
	}

	private String toLowerCaseAndCir(String s) {
		String x = s.toLowerCase();
		x = CyrillicLatinConverter.lat2cir(x);
		return x;
	}

}
