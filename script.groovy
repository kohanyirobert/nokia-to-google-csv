@Grab('com.xlson.groovycsv:groovycsv:1.1')
import com.xlson.groovycsv.CsvParser

import java.nio.charset.StandardCharsets

def NOKIA_COLS = [
  "Name",
  "Given Name",
  "Additional Name",
  "Family Name",
  "Yomi Name",
  "Given Name Yomi",
  "Additional Name Yomi",
  "Family Name Yomi",
  "Name Prefix",
  "Name Suffix",
  "Initials",
  "Nickname",
  "Short Name",
  "Maiden Name",
  "Birthday",
  "Gender",
  "Location",
  "Billing Information",
  "Directory Server",
  "Mileage",
  "Occupation",
  "Hobby",
  "Sensitivity",
  "Priority",
  "Subject",
  "Notes",
  "Group Membership",
  "E-mail 1 - Type",
  "E-mail 1 - Value",
  "Phone 1 - Type",
  "Phone 1 - Value",
  "Phone 2 - Type",
  "Phone 2 - Value",
  "Phone 3 - Type",
  "Phone 3 - Value",
]

def out = new PrintStream(System.out, true, 'utf8')

out.println NOKIA_COLS.join(',')

new CsvParser().parse(new FileReader(this.args[0]),
  readFirstLine: false,
  separator: ';',
  quoteChar: '"').each {
  def givenName = it['Utónév']
  def familyName = it['Vezetéknév']
  def jobPosition = it['Beosztás']
  def companyName = it['Vállalat']
  def emailAddress = it['Általános e-mail cím']
  def name = "$familyName $givenName"
  if (jobPosition) {
    name += " $jobPosition"
  }
  if (companyName) {
    name += " $companyName"
  }
  def phoneNumbers = [
    it['Általános mobilszám'],
    it['Általános telefonszám'],
    it['Általános fax'],
    it['Otthoni mobiltelefonszám'],
    it['Otthoni vezetékes telefonszám'],
    it['Munkahelyi mobiltelefonszám'],
    it['Munkahelyi telefonszám'],
  ].grep { it }
  out.println([
    name,
    givenName,
    '',
    familyName,
    ',' * 15,
    jobPosition,
    ',' * 4,
    '* My Contacts',
    emailAddress ? '*' : '',
    emailAddress,
    *phoneNumbers.collect { ['*', it] }.flatten(),
    ',' * (7 - phoneNumbers.size())
  ].join(','))
}
