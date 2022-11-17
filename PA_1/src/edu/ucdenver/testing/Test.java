package edu.ucdenver.testing;

import edu.ucdenver.tournament.Tournament;

import java.time.LocalDate;

public class
Test {
    public static void main(String[] args) {


        LocalDate d;

        Tournament tournament = new Tournament("2022 FIFA World Cup",
                d = LocalDate.parse("2022-11-20"), d = LocalDate.parse("2022-12-18"));

        tournament.addCountry("USA");
        tournament.addCountry("Mexico");
        tournament.addCountry("Canada");

        tournament.addTeam("USA National Team", "USA");
        tournament.addTeam("Mexico National Team", "Mexico");
        tournament.addTeam("Canada National Team", "Canada");

        tournament.addReferee("John Doe", "USA");
        tournament.addReferee("Jane Doe", "USA");
        tournament.addReferee("Jenny Smith", "Canada");

        tournament.addPlayer("USA National Team", "Josh Smith", 25, 175.4, 150);
        tournament.addPlayer("USA National Team", "Jake Brown", 25, 175.4, 150);

        tournament.addMatch(d = LocalDate.parse("2022-11-20"), "USA National Team", "Mexico National Team");
        tournament.addMatch(d = LocalDate.parse("2022-11-21"), "USA National Team", "Mexico National Team");
        tournament.addMatch(d = LocalDate.parse("2022-12-21"), "USA National Team", "Mexico National Team");
        tournament.addMatch(d = LocalDate.parse("2022-09-21"), "USA National Team", "Canada National Team");

        tournament.addRefereeToMatch(d = LocalDate.parse("2022-11-20"), "Jenny Smith");

        tournament.addPlayerToMatch(d = LocalDate.parse("2022-11-20"), "USA National Team", "Josh Smith");

        tournament.setMatchScore(d = LocalDate.parse("2022-09-21"), 12, 17);

        //System.out.println(tournament);
        //System.out.println(tournament.getParticipatingCountries());
        System.out.println(tournament.getListTeams());
        System.out.println("==========================================================================================");
    System.out.println("\n" + tournament.getListMatches());
//    System.out.println("\n" + tournament.getListReferees());
//
//    System.out.println();
       System.out.println("\n" + tournament.getUpcomingMatches());
//    System.out.println("\n" + tournament.getMatchesOn(d = LocalDate.parse("2022-11-20")));
//    System.out.println("\n" + tournament.getMatchLineUps(d = LocalDate.parse("2022-11-21")));
//    System.out.println("\n" + tournament.getMatchesFor("USA National Team"));
//    }
    }
}
