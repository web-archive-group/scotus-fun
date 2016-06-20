import urllib2
import time

# IA Wayback more robust.
WAYBACK = 'http://web.archive.org/web/'

def get_capture_dates(url, _wayback):
    start_state = '%s1/%s' % (_wayback, url)
    try:
        start = urllib2.urlopen(start_state)
        start_url = start.geturl()
        start_txt = start_url.split('/')[4]

    except urllib2.HTTPError as e:
        start_txt = e.code


    end_state = '%s2/%s' % (_wayback, url)
    try:
        end = urllib2.urlopen(end_state)
        end_url = end.geturl()
        end_txt = end_url.split('/')[4]
    except urllib2.HTTPError as e:
        end_txt = e.code

    print ','.join([url, str(start_txt), str(end_txt)])
    return

# SCOTUS seeds (a selection).
scotus_seeds = [
"http://biden.senate.gov/",
"http://chafee.senate.gov/",
"http://coburn.senate.gov/",
"http://cornyn.senate.gov/",
"http://democrats.senate.gov/AskRoberts/",
"http://dewine.senate.gov/",
"http://en.wikipedia.org/wiki/Alito",
"http://en.wikipedia.org/wiki/John_G._Roberts,_Jr.",
"http://landrieu.senate.gov/scotus/index.cfm",
"http://lgraham.senate.gov/",
"http://speaker.house.gov/",
"http://thomas.loc.gov/cgi-bin/ntquery/z?nomis:109PN0078600:",
"http://thomas.loc.gov/cgi-bin/ntquery/z?nomis:109PN0080100:",
"http://thomas.loc.gov/cgi-bin/ntquery/z?nomis:109PN0097800:",
"http://usinfo.state.gov/dhr/Archive/2005/Jul/20-330984.html",
"http://www.archives.gov/news/john-roberts",
"http://www.loc.gov/rr/law",
"http://www.senate.gov/reference/reference_index_subjects/Nominations_vrd.htm",
"http://www.supremecourtus.gov/oral_arguments/argument_transcripts.html",
"http://www.uscourts.gov/news.html",
"http://www.usdoj.gov/olp/roberts.htm",
"http://www.whitehouse.gov/infocus/judicialnominees",]

for item in scotus_seeds:
    time.sleep(5) # Be nice
    get_capture_dates(item, WAYBACK)
