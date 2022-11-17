# CSCI-3920_PA1
new version of PA repo since we didn't have JavaFX project and made a mess trying to set one up  ._. 

### Team Member Names:
- Alexa Selby
- Alina Burlacu

## Description
Your team will implement a system to manage the matches and
lineups for the upcoming Fifa Worldcup Qatar 2022. Your system
will rely on a Client/Server architecture allowing admins to load
the matches and players in the lineup for each game and record
the matches’ scores. Further, there will be an application for the
general public to query the lineups and matches’ results.

## INSTRUCTIONS:
    1) Run `ServerApplication`
    2) Select ADMIN or USER from menu
    3) Start server 
    4) Run `AdminApp` or `UserApp` depending on which client you choose
## Requirements
### 1. Load from file
• An error is shown if the file does not exist.

#### Status: NOT working in domain logic OR in GUI

---

### 2. Save to file
• If a previous file exists, it will be overwritten.

#### Status: NOT working in domain logic OR in GUI

---

### 3. Create a new tournament.  
   • E.g., Worldcup Qatar 2022.

#### Status: working in both domain logic AND in GUI

---

### 4. Add a participating country.  
   • E.g., USA, Spain, etc.
   • An exception is expected when the country is already in the system.

#### Status: working in both domain logic AND in GUI

---

### 5. Add a national team representing a country to the tournament.  
   • E.g., the USA national team, Spain national team, etc.  
   • An exception is expected when the country does not exist, or another team has the same
   name.

#### Status: working in both domain logic AND in GUI

---

### 6. Add referees to the tournament.  
   • A referee participates in the tournament representing a country.
   • An exception is expected when another referee with the same name exists in the system.

#### Status: working in both domain logic AND in GUI

---

### 7. Add a player to a national team squad.  
   • Each national team may have up to 35 players in its squad.  
   • An exception is expected when another player with the same name already exists on the
   team or when the team cannot be found in the system.

#### Status: working in both domain logic AND in GUI

---

### 8. Add a match on a particular date and time between two national teams.
   • For simplicity, only one match  can  occur at a particular  time⏤ as we are not modeling
   the stadiums.  
   • An exception is expected when a match is already in place for the given date/time, when
   either team cannot be found in the system or when both match’s teams are the same.

#### Status: working in both domain logic AND in GUI

---

### 9. Assign a referee to a match.  
   • Four referees are required for a match to take place.  
   • The nationality of the referees must be validated. A referee’s nationality cannot be any of
   the matches' countries.  
   • An exception is expected when these rules are violated, or either math or referee cannot
   be found in the system.

#### Status: working in both domain logic AND in GUI

---

### 10. Add a player to a national team’s lineup for a particular match.  
• A team’s lineup is the set of 11 players from the team’s squad who will play the game. For
    simplicity, we do not consider substitute players.  
• The player must be in the national team’s squad (point 7). 
• An exception is expected when rules are violated.

#### Status: working in both domain logic AND in GUI

---

### 11. Record the score of a completed match.
• A match’s date/time should be in the past. 
• Only the final score will be recorded. 
• An  exception  is expected  when  a  match  cannot  be  found  for  the given  date  or  is  not  a past match.

#### Status: working in both domain logic AND in GUI

---

### 12. Get a list of the upcoming matches, listing the date/time and the name of the two teams for each game.

#### Status: working in domain logic but NOT working in GUI

---

### 13. Get a list of matches on a particular date (no time)

#### Status: working in domain logic but NOT working in GUI

---

### 14. Get a list of all games for a specific team. Past matches should include the score.

#### Status: working in domain logic but NOT working in GUI

---

### 15. Get the lineups for a match (either past or future). 

#### Status: working in domain logic but NOT working in GUI

---

## Additional Notes:
- The domain logic functions completely with a few potential minor bugs having to do with not throwing exceptions when necessary
- The client and server seem to be communicating mostly correctly, but with a few potential bugs
- The Client Admin GUI functions similarly to the domain logic and implements Requirements 1-11 from the above list of requirements
- The Client User (General Public) GUI is fully formatted, but is not populating with data. The User GUI is meant to implement requirements 12-15.