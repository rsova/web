import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack
import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject
import com.mongodb.Mongo
import groovy.io.FileType
import org.json.JSONObject
import com.mongodb.util.JSON
import groovy.xml.*
import org.json.XML
import service.AppServiceHandler
import app.AppModule
//import service.mongo.MongoService

ratpack {
	
	bindings {
		add new AppModule(launchConfig.baseDir.file("config/Config.groovy").toFile())
	}

  handlers {AppServiceHandler appHandler ->
	get("/:name?"){ next()}
	
	get("base/") {
		response.send "Running at :" + launchConfig.baseDir
	  }
    get("/tracks"){
		
def trac =
'''
{ "items": [ { "model": "Genre", "items": [ { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 96, "text": "Introduction", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 189, "text": "Every Day I Have The Blues", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 167, "text": "How Blue Can You Get?", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 141, "text": "Worry, Worry, Worry", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 144, "text": "3 O'Clock Blues", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 97, "text": "Darlin' You Know I Love You", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 175, "text": "Sweet Sixteen", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "The Thrill Is Gone", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 155, "text": "Please Accept My Love", "items": [

], "info": "", "leaf": true } ], "text": "Live in Cook County Jail", "info": "<p>Live in Cook County Jail is a 1971 live album by B.B. King recorded in Cook County Jail, Chicago, Illinois. It was ranked as number 499 in the book version of Rolling Stone's 500 Greatest Albums of All Time.</p>", "leaf": false } ], "text": "B.B.King", "info": "<p>Riley B. King aka B. B. King (born September 16th, 1925 in Itta Bena, Mississippi) is a well known American blues guitarist and songwriter. He is among the most respected electric guitarists. </p><p>One of Kingâ€™s trademarks is naming his guitar (Gibson ES335) â€œLucilleâ€. In the 1950s in a bar in Twist, Arkansas two men got into a fight, accidentally knocking over a bucket of burning kerosene (used for heating) and setting the establishment on fire. Risking his life, B.B. King ran back into the collapsing building to retrieve his guitar.</p>", "leaf": false }, { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 141, "text": "A Hard Road", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 149, "text": "It's Over", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 169, "text": "You Don't Love Me", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 149, "text": "The Stumble", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 125, "text": "Another Kind Of Love", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 119, "text": "Hit The Highway", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 132, "text": "Leaping Christine", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 140, "text": "Dust My Blues", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 100, "text": "There's Always Work", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 146, "text": "The Same Way", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 105, "text": "The Supernatural", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 112, "text": "Top Of The Hill", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 145, "text": "Someday After A While (You'll Be Sorry)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 181, "text": "Living Alone", "items": [

], "info": "", "leaf": true } ], "text": "A Hard Road", "info": "<p>A Hard Road is a 1967 electric blues album recorded by John Mayall & the Bluesbreakers featuring Peter Green on lead guitar, John McVie on bass, Aynsley Dunbar on drums and John Almond. Tracks 5, 7 and 13 feature the horn section of Alan Skidmore and Ray Warleigh. Peter Green sings lead vocals on â€œYou Don't Love Meâ€ and â€œThe Same Way.â€ The notable instrumental track â€œThe Supernaturalâ€, a guitar improvisation in the key of D minor, has much in common with Peter Green's later hit composition â€œBlack Magic Womanâ€.</p>", "leaf": false }, { "model": "Album", "items": [ { "model": "Track", "duration": 115, "text": "All your love", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 177, "text": "Hideaway", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 118, "text": "Little Girl", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 108, "text": "Another Man", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "Double Crossin' Time", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 155, "text": "What'd I Say", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 144, "text": "Key to Love", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 141, "text": "Parchman Farm", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 156, "text": "Have you Heard", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "All your love", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 103, "text": "Hideaway", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 145, "text": "It ain't Right", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 172, "text": "Lonely Years", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 99, "text": "Bernard Jenkins", "items": [

], "info": "", "leaf": true } ], "text": "The Beano Album", "info": "<p>Blues Breakers is a 1966 electric blues album credited to John Mayall with Eric Clapton. The band name John Mayall & the Bluesbreakers, that was used by the band consequently is derived from the title of this album, no original issues mention The Bluesbreakers as band name. The album was also known as The Beano Album because of its cover photograph showing Clapton reading The Beano, a well-known British children's comic. Clapton stated in his autobiography that he was reading Beano on the cover because he felt like being 'uncooperative' during the photo shoot.</p>", "leaf": false } ], "text": "John Mayall's Bluesbreakers", "info": "<p>John Mayall & the Bluesbreakers are a pioneering English blues band, led by singer, songwriter, and multi-instrumentalist John Mayall, OBE. Mayall used the band name between 1963 and â€˜67 then dropped it for some fifteen years, but in 1982 a â€˜Return of the Bluesbreakersâ€™ was announced and it has been kept since then. The name has become generic without a clear distinction which recordings are to be credited just to the leader or to leader and his band. </p>", "leaf": false }, { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 113, "text": "Scuttle Buttin'", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 99, "text": "Couldn't Stand The Weather", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 146, "text": "The Things (That) I Used To Do", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 162, "text": "Voodoo Chile (Slight Return)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 96, "text": "Cold Shot", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 123, "text": "Tin Pan Alley", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 166, "text": "Honey Bee", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 132, "text": "Stang's Swang", "items": [

], "info": "", "leaf": true } ], "text": "Couldn't stand the weather", "info": "<p>Couldn't Stand the Weather is the second studio album by Stevie Ray Vaughan and Double Trouble, released in 1984.</p>", "leaf": false }, { "model": "Album", "items": [ { "model": "Track", "duration": 134, "text": "Say What!", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 98, "text": "Lookin' Out The Window", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 152, "text": "Look At Little Sister", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 105, "text": "Ain't Gone 'n' Give Up On Love", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 111, "text": "Gone Home", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 135, "text": "Change It", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 124, "text": "You'll Be Mine", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 107, "text": "Empty Arms", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 116, "text": "Come On (Part III)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 106, "text": "Life Without You", "items": [

], "info": "", "leaf": true } ], "text": "Soul to soul", "info": "<p>Soul to Soul is the third studio album by Stevie Ray Vaughan and Double Trouble, and was released in 1985. It has been certified platinum by RIAA. Soul to Soul saw the addition of a new band member, keyboardist Reese Wynans, to the Double Trouble power trio. Wynans would stay with the group until Vaughan's death in 1990.</p>", "leaf": false }, { "model": "Album", "items": [ { "model": "Track", "duration": 158, "text": "Love Struck Baby", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 120, "text": "Pride And Joy", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 165, "text": "Texas Flood", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 129, "text": "Tell Me", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 166, "text": "Testify", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 106, "text": "Rude Mood", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "Mary Had A Little Lamb", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 170, "text": "Dirty Pool", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 120, "text": "I'm Cryin'", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "Lenny", "items": [

], "info": "", "leaf": true } ], "text": "Texas Flood", "info": "<p>Texas Flood is the debut album of American blues musician Stevie Ray Vaughan and his band Double Trouble, released June 13, 1983 on Epic Records. The album was recorded in only three days, at Jackson Browne's personal recording studio, in 1982 since the band had been playing many live sets beforehand.</p><p>More popular than any blues album in nearly twenty years, Texas Flood was a surprise success for Vaughan, who had labored in obscurity for years. On the North American Billboard Music Charts, Texas Flood peaked at #64 and #38 on the Billboard 200 and Pop Albums charts, respectively. The single â€œPride and Joyâ€ peaked at #20 on the Mainstream Rock chart. The album was Grammy nominated in 1983 for â€œBest Blues Recordingâ€ along with â€œRude Moodâ€, which was nominated for â€œBest Blues Instrumental Performanceâ€.</p>", "leaf": false } ], "text": "Stevie Ray Vaughan", "info": "<p>Stephen (â€œStevieâ€) Ray Vaughan, born in Dallas, Texas (October 3, 1954 â€“ August 27, 1990) was an American blues guitar legend, and is known as one of the most influential blues musicians in history.</p><p>Stevie Ray had been in numerous bands before joining blues rock combo Stevie Ray Vaughan and Double Trouble in the late 1970â€™s.</p><p>Stevie Ray Vaughan and Double Troubleâ€™s debut album was released in 1983. The critically acclaimed Texas Flood (1983) featured the top-20 hit Pride And Joy and sold well in both blues and rock circles.</p>", "leaf": false } ], "text": "Blues", "info": "", "leaf": false }, { "model": "Genre", "items": [ { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 147, "text": "Wednesday Night Prayer Meeting", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 130, "text": "Cryin' Blues", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 126, "text": "Moanin'", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 142, "text": "Tensions", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 90, "text": "My Jelly Roll Soul", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 146, "text": "E's Flat Ah's Flat Too", "items": [

], "info": "", "leaf": true } ], "text": "Blues and Roots", "info": "<p>Blues & Roots is an album by Charles Mingus, recorded in 1959 and released in 1960. It has been reissued twice as a CD, first by Atlantic Records, and then again by Rhino Entertainment in 1998.</p>", "leaf": false }, { "model": "Album", "items": [ { "model": "Track", "duration": 112, "text": "Better Git It In Your Soul", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 168, "text": "Goodbye Pork Pie Hat", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 121, "text": "Boogie Stop Shuffle", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 124, "text": "Self-Portrait In Three Colors", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 139, "text": "Open Letter To Duke", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 166, "text": "Bird Calls", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 167, "text": "Fable Of Faubus", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 122, "text": "Pussy Cat Dues", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 120, "text": "Jelly Roll", "items": [

], "info": "", "leaf": true } ], "text": "Mingus Ah Um", "info": "<p>Mingus Ah Um is a jazz album by Charles Mingus, recorded and released on Columbia Records in 1959, as CK 65512. It was his first album recorded for Columbia. The cover features a painting by S. Neil Fujita.</p>", "leaf": false } ], "text": "Charles Mingus", "info": "<p>Charles Mingus (22nd April 1922 â€“ 5th January 1979) was an American jazz bassist, composer, bandleader, and occasional pianist. He was also known for his activism against racial injustice.</p><p>Mingusâ€™ legacy is notable: he is ranked among the finest composers and performers in jazz, and recorded many highly regarded albums. Dozens of musicians passed through his bands and later went on to impressive careers.</p>", "leaf": false }, { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 104, "text": "Part I: Acknowledgement", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 96, "text": "Part II: Resolution", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 100, "text": "Part III: Pursuance", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 111, "text": "Part IV: Psalm", "items": [

], "info": "", "leaf": true } ], "text": "A Love Supreme", "info": "<p>A Love Supreme is a jazz studio album recorded by John Coltrane's quartet in December 1964[13] and released by Impulse! Records (catalogue number AS-77) in February 1965. It is generally considered to be among Coltrane's greatest works, as it melded the hard bop sensibilities of his early career with the free jazz style he adopted later.</p>", "leaf": false }, { "model": "Album", "items": [ { "model": "Track", "duration": 139, "text": "My Favourite Things", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 138, "text": "Everytime we say Goodbye", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 136, "text": "Summertime", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 183, "text": "But not for me", "items": [

], "info": "", "leaf": true } ], "text": "My Favorite Things", "info": "<p>My Favorite Things is a 1961 jazz album by John Coltrane. It was the first session recorded by Coltrane on the Atlantic Records label (as SD-1361), and the first to introduce his new quartet featuring McCoy Tyner (piano), Elvin Jones (drums) and Steve Davis (bass) -- neither Jimmy Garrison nor Reggie Workman featured as yet.</p> <p>The album was also the first to quite clearly mark Coltrane's change from bebop to free- and modal jazz, which was slowly becoming apparent in some of his previous releases. It introduces complex harmonic reworkings of such jazz standards as â€œMy Favorite Thingsâ€ and â€œBut Not for Meâ€. Additionally, at a time when the soprano saxophone was little used in jazz, it demonstrated Coltrane's further investigation of the instrument's capabilities.</p>", "leaf": false } ], "text": "John Coltrane", "info": "<p>John William Coltrane (Hamlet, North Carolina, September 23, 1926 â€“ Huntington, New York, July 17, 1967) was an American jazz saxophonist and composer.</p><p>American jazz great John Coltrane emerged in the 1950s, playing tenor and soprano sax with Dizzy Gillespie, Miles Davis and Thelonious Monk. A leader of â€œhard bopâ€, in the 1960s he led his own groups and changed the face of jazz with experimentation and improvisation, his later recordings reflecting his belief that music was a form of spiritual expression.</p>", "leaf": false }, { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 113, "text": "So What", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 150, "text": "Freddie Freeloader", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 121, "text": "Blue in Green", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 134, "text": "All Blues", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 185, "text": "Flamenco Sketches", "items": [

], "info": "", "leaf": true } ], "text": "Kind of Blue", "info": "<p>Kind of Blue is a studio album by American jazz musician Miles Davis, released August 17, 1959, on Columbia Records in the United States. Recording sessions for the album took place at Columbia's 30th Street Studio in New York City on March 2 and April 22, 1959. The sessions featured Davis's ensemble sextet, which consisted of pianists Bill Evans and Wynton Kelly, drummer Jimmy Cobb, bassist Paul Chambers, and saxophonists John Coltrane and Julian â€œCannonballâ€ Adderley. After the inclusion of Bill Evans into his sextet, Davis followed up on the modal experimentations of Milestones (1958) and 1958 Miles (1958) by basing the album entirely on modality, in contrast to his earlier work with the hard bop style of jazz.</p>", "leaf": false }, { "model": "Album", "items": [ { "model": "Track", "duration": 153, "text": "'Round Midnight", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 170, "text": "Ah-Leu-Cha", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 152, "text": "All of You", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 100, "text": "Bye Bye Blackbird", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 182, "text": "Tadd's Delight", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 141, "text": "Dear Old Stockholm", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 113, "text": "Two Bass Hit", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 155, "text": "Little Melonae", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 183, "text": "Budo", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 184, "text": "Sweet Sue, Just You", "items": [

], "info": "", "leaf": true } ], "text": "Round About Midnight", "info": "<p>'Round About Midnight is an album by jazz musician Miles Davis. It was his debut on Columbia Records, and was originally released in March 1957 (CL 949). The album took its name from the Thelonious Monk song â€œ'Round Midnightâ€.</p>", "leaf": false } ], "text": "Miles Davis", "info": "<p>Miles Davis (Miles Dewey Davis III, Alton, Illinois, May 25, 1926 â€“ Santa Monica, California, September 28, 1991) was an American trumpeter, bandleader and composer.</p><p>Davis was at the forefront of almost every major development in jazz after World War II. He was one of the most influential and innovative musicians of the 20th century.</p><p>Widely considered one of the most influential musicians of the 20th century, Miles Davis was, with his musical groups, at the forefront of several major developments in jazz music, including bebop, cool jazz, hard bop, modal jazz, and jazz fusion.</p>", "leaf": false } ], "text": "Jazz", "info": "", "leaf": false }, { "model": "Genre", "items": [ { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 101, "text": "Space Oddity", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 154, "text": "John, I'm Only Dancing", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 105, "text": "Changes", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 181, "text": "Ziggy Stardust", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 169, "text": "Suffragette City", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "The Jean Genie", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 162, "text": "Diamond Dogs", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 125, "text": "Rebel Rebel", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 130, "text": "Young Americans", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 129, "text": "Fame ('90 Remix)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 131, "text": "Golden Years", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 186, "text": "Heroes", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 110, "text": "Ashes to Ashes", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 118, "text": "Fashion", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 113, "text": "Let's Dance", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 120, "text": "China Girl", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 181, "text": "Modern Love", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "Blue Jean", "items": [

], "info": "", "leaf": true } ], "text": "Changesbowie", "info": "<p>Changesbowie is a compilation album by David Bowie, released in 1990 in the United States by Rykodisc and by EMI in the UK as part of Rykodisc's Bowie remastering program, to replace the deleted RCA Records compilation Changesonebowie.</p><p>While the cover artwork was generally dismissed as amateurish (â€œa sixth-form cut 'n' paste collageâ€, according to author David Buckley), the collection made #1 in the UK, giving Bowie his first chart-topping album since Tonight in 1984.</p>", "leaf": false }, { "model": "Album", "items": [ { "model": "Track", "duration": 107, "text": "Space Oddity", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 156, "text": "Unwashed and Somewhat Slightly Dazed", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 103, "text": "(Don't Sit Down)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 137, "text": "Letter to Hermione", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 90, "text": "Cygnet Committee", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 181, "text": "Janine", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 138, "text": "An Occasional Dream", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 105, "text": "Wild Eyed Boy From Freecloud", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 127, "text": "God Knows I'm Good", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 136, "text": "Memory of a Free Festival", "items": [

], "info": "", "leaf": true } ], "text": "Space Oddity", "info": "<p>Space Oddity is a 1969 album by rock musician David Bowie. Originally released by Philips in the UK as David Bowie and by Mercury in the U.S. as Man of Words/Man of Music, it was reissued by RCA Records in 1972 under its current title.</p>", "leaf": false } ], "text": "David Bowie", "info": "<p>David Bowie (born David Robert Jones on 8th January 1947 in Brixton, London, England, United Kingdom) is an English singer, songwriter, musician, and actor. Active in five decades of popular music and frequently reinventing his music and image, Bowie is widely regarded as an innovator, particularly for his work in the 1970s. He has been cited as an influence by many musicians and is known for his distinctive voice and the intellectual depth of his work.</p>", "leaf": false }, { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 102, "text": "Come Together", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 170, "text": "Something / Jam", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 167, "text": "Maxwell's Silver Hammer", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 132, "text": "Oh! Darling", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 102, "text": "Octopus's Garden", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 118, "text": "I Want You", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 159, "text": "Come and Set It", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 134, "text": "Oh, I Want You", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 172, "text": "Because", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 182, "text": "You Never Give Me Your Money", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 143, "text": "Sun King", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 126, "text": "Mean Mr. Mustard", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 144, "text": "Her Majesty", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 175, "text": "Polythene Pam", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 97, "text": "She Came in Through the Bathroom Window", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 150, "text": "Golden Slumbers", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "Carry That Weight", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 175, "text": "The End", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 144, "text": "Ain't She Sweet (Rock'n'Roll Jam)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 113, "text": "Something (Harrison Solo)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 170, "text": "Maxwell's Silver Hammer (take 5)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 91, "text": "Octopus's Garden (take 2)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 93, "text": "You Never Give Me Your Money (take 30)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 112, "text": "Golden Slumbers / Carry That Weight (take 13)", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 119, "text": "The End (remix)", "items": [

], "info": "", "leaf": true } ], "text": "Abbey Road", "info": "<p>Abbey Road is the eleventh official album released by The Beatles. Though work on Abbey Road began in April 1969, making it the twelfth and final album recorded by the band; Let It Be was the last album released before the Beatlesâ€™ dissolution in 1970. Abbey Road was released on 26 September 1969 in the United Kingdom, and 1 October 1969 in the United States. It was produced and orchestrated by George Martin for Apple Records. Geoff Emerick was engineer, Alan Parsons was assistant engineer, and Tony Banks was tape operator. It is regarded as one of The Beatlesâ€™ most tightly constructed albums, although the band was barely operating as a functioning unit at the time. Rolling Stone magazine named it the 14th greatest album of all time.</p>", "leaf": false }, { "model": "Album", "items": [ { "model": "Track", "duration": 155, "text": "Taxman", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 154, "text": "Eleanor Rigby", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 97, "text": "I'm Only Sleeping", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 145, "text": "Love You To", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 180, "text": "Here, There and Everywhere", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 139, "text": "Yellow Submarine", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 147, "text": "She Said, She Said", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "Good Day Sunshine", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 101, "text": "And Your Bird Can Sing", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 159, "text": "For No One", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 114, "text": "Doctor Robert", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 170, "text": "I Want to Tell You", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 179, "text": "Got to Get You into My Life", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 186, "text": "Tomorrow Never Knows", "items": [

], "info": "", "leaf": true } ], "text": "Revolver", "info": "<p>Revolver is the seventh album by English rock group The Beatles, released on 5 August 1966. Many of the tracks on Revolver are marked by an electric guitar-rock sound, in contrast with their previous, folk rock inspired Rubber Soul. It reached number one on both the British chart and American chart and stayed at the top spot for seven weeks and six weeks, respectively.</p> <p>Placed at number 3 in the Rolling Stone magazine's list of the 500 greatest albums of all time, the album is often regarded as one of the greatest achievements in rock music history and one of The Beatles' greatest studio achievements.</p>", "leaf": false } ], "text": "The Beatles", "info": "<p>The Beatles were an iconic rock group from Liverpool, England. They are frequently cited as the most commercially successful and critically acclaimed band in modern history, with innovative music, a cultural impact that helped define the 1960s and an enormous influence on music that is still felt today. Currently, The Beatles are one of the two musical acts to sell more than 1 billion records, with only Elvis Presley having been able to achieve the same feat.</p>", "leaf": false } ], "text": "Pop", "info": "", "leaf": false }, { "model": "Genre", "items": [ { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 147, "text": "Hells Bells", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 108, "text": "Shoot to Thrill", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 121, "text": "What do you do for money honey", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 108, "text": "Give the dog a bone", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 127, "text": "Let me put my love into you", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 190, "text": "Back in Black", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 163, "text": "You shook me all night long", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 121, "text": "Have a drink on me", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 187, "text": "Shake a leg", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 90, "text": "Rock and roll ain't noise pollution", "items": [

], "info": "", "leaf": true } ], "text": "Back in Black", "info": "<p>Back in Black is an album by Australian rock band AC/DC. It is the seventh Australian and sixth internationally released studio album by the band.</p><p>Released on 25 July 1980, Back in Black was the first AC/DC album recorded without former lead singer Bon Scott, who died on 19 February 1980 at the age of 33, and was dedicated to him. The band considered disbanding following Scott's death, but they ultimately decided to continue and shortly thereafter hired Brian Johnson as their new lead singer and lyricist. Producer Robert John â€œMuttâ€ Lange, who had previously worked with AC/DC on Highway to Hell, was again brought in to produce. The recordings were made at Compass Point Studios in Nassau, Bahamas, and Electric Lady Studios in New York, where the album was also mixed.</p>", "leaf": false } ], "text": "AC/DC", "info": "<p>AC/DC are an Australian rock band, formed in 1973 by brothers Malcolm and Angus Young. Although the band are commonly classified as hard rock and are considered a pioneer of heavy metal, they have always classified their music as rock and roll. To date they are one of the highest grossing bands of all time. AC/DC underwent several line-up changes before releasing their first album, High Voltage, in 1975. Membership remained stable until bassist Mark Evans was replaced by Cliff Williams in 1978 for the album Powerage. Within months of recording the album Highway to Hell, lead singer and co-songwriter Bon Scott died on 19 February 1980, after a night of heavy alcohol consumption. The group briefly considered disbanding, but Scott's parents urged them to continue and hire a new vocalist. Ex-Geordie singer Brian Johnson was auditioned and selected to replace Scott. Later that year, the band released their highest selling album, Back in Black.</p>", "leaf": false }, { "model": "Artist", "items": [ { "model": "Album", "items": [ { "model": "Track", "duration": 167, "text": "Smells Like Teen Spirit", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 98, "text": "In Bloom", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 120, "text": "Come As You Are", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 172, "text": "Breed", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 187, "text": "Lithium", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 108, "text": "Polly", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 106, "text": "Territorial Pissings", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 177, "text": "Drain You", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 182, "text": "Lounge Act", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 125, "text": "Stay Away", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 91, "text": "On A Plain", "items": [

], "info": "", "leaf": true }, { "model": "Track", "duration": 120, "text": "Something In The Way", "items": [

], "info": "", "leaf": true } ], "text": "Nevermind", "info": "This seminal album changed the face of music forever.", "leaf": false } ], "text": "Nirvana", "info": "Nirvana's raw music was a reaction against the glam rock of the previous decade.", "leaf": false } ], "text": "Rock", "info": "", "leaf": false } ] }
'''
response.send json
	}
	get("mock") {
		def mock = 		'''
{
  "product": {
    "niin": "016033650",
    "price": "154.26",
    "assignmentDate": "2012-03-01"
  },
  "suppliers": [
    {
      "name": "DEFENSE MEDICAL STANDARDIZATION",
      "cageCode": "64616",
      "address": "1423 SULTON DR, FREDERICK, MD, UNITED STATES, 21702-5013",
      "phone": "301-619-2001",
      "cao": "S2101A",
      "adp": "HQ0338",
      "isWomanOwned": "N"
    },
    {
      "name": "KEY SURGICAL, INC.",
      "cageCode": "0SRE6",
      "address": "8101 WALLACE RD, EDEN PRAIRIE, MN, UNITED STATES, 55344-2114",
      "phone": "9529149789 9529149866",
      "cao": "S2401A",
      "adp": "HQ0339",
      "isWomanOwned": "N"
    }
  ]
}
'''
		
		response.send mock
	}
  	
	prefix("lookup/:niins?", appHandler)
	//prefix("partlink/:niins?", appHandler)
	
    assets "public", "index.html"
    //assets "public"
  }
}
