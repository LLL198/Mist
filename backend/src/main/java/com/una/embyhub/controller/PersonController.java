package com.una.embyhub.controller;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.popularperson.PopularPersonResultsPage;
import info.movito.themoviedbapi.model.people.PersonDb;
import info.movito.themoviedbapi.model.people.credits.CombinedPersonCredits;
import info.movito.themoviedbapi.tools.TmdbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"person"})
public class PersonController {
   @Autowired
   private TmdbApi tmdbApi;

   @PostMapping({"{personId}"})
   public PersonDb getPersonDetails(@PathVariable int personId, @RequestParam String language) throws TmdbException {
      return this.tmdbApi.getPeople().getDetails(personId, language);
   }

   @PostMapping({"getPeopleCombinedCredits"})
   public CombinedPersonCredits getPeopleCombinedCredits(@RequestParam int personId, @RequestParam String language) throws TmdbException {
      return this.tmdbApi.getPeople().getCombinedCredits(personId, language);
   }

   @PostMapping({"getPeoplePopular"})
   public PopularPersonResultsPage getPeoplePopular(@RequestParam String language, @RequestParam int page) throws TmdbException {
      return this.tmdbApi.getPeopleLists().getPopular(language, page);
   }

   @PostMapping({"getSearchPerson"})
   public PopularPersonResultsPage getSearchPerson(
      @RequestParam String query, @RequestParam Boolean includeAdult, @RequestParam String language, @RequestParam int page
   ) throws TmdbException {
      return this.tmdbApi.getSearch().searchPerson(query, includeAdult, language, page);
   }
}
