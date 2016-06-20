# analyze scotus web text 


# get list of links: 
grep --no-filename ^http: *.arc > all_links.txt 
grep text/html all_links.txt > web_pages_roberts1.txt 
uniq all_links.txt | sort > all_links_sorted.txt
cat all_links_sorted.txt | wc -l # count of uniq capture urls.


# roberts-senate-gov-text.txt is the text file exported from warcbase.
# There is one line per web page in the warc/arc file.

grep -i 'roberts' roberts-senate-gov-text.txt > roberts-only.txt

# Get the line / word counts for Roberts Senate crawls and roberts-only (those that mention 'roberts')
wc roberts-senate-gov-text.txt 
wc roberts-only.txt 

# WARNING: There is also a SENATOR Roberts, so roberts-only.txt is not as clean as you want.

# Break down the roberts-only captures into just the words, case insensitive
tr -sc 'a-zA-Z' '\012' < roberts-only.txt | tr '[:upper:]' '[:lower:]' > roberts-only-words.txt

# Feed roberts-only.txt to wordle: http://www.wordle.net/create to create the "bad" word cloud.

# create a concordance of the roberts-senate captures.
cat roberts-only-words.txt | sort | uniq -c | sort -nr > roberts-only-concordance.txt

