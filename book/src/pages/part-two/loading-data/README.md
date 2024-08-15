# Loading Data

We'll start our exploration of data by looking at climate data. Climate data is readily available and climate is a topical issue. 

To start we'll need some data.  ... data location ... HadCRUT5 temperature anomaly data.
Description.

HadCRUT.5 data were obtained from http://www.metoffice.gov.uk/hadobs/hadcrut5 on 27 October 2023 and are © British Crown Copyright, Met Office 2021, provided under an Open Government License, http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/

This data set has one reading for each month from 1850 to 2023, making 2084 readings. This gives us enough data that working with it will be interesting but not so much that it's overwhelming.

If we look at the file we see it looks like the following:

``` csv
Time,Anomaly (deg C),Lower confidence limit (2.5%),Upper confidence limit (97.5%)
1850-01,-0.67456436,-0.98177195,-0.3673568
1850-02,-0.333416,-0.700901,0.034069024
1850-03,-0.59132266,-0.9339191,-0.2487262
1850-04,-0.58872116,-0.8982594,-0.27918297
```

This type of file is known as comma separated values (CSV). 
Each line, except the first, consists of values, otherwise known as fields.
Each field is separated by a comma.
The first line, the heading, gives names for the fields.
So we can see that the fields are, in order, time, temperature anomaly, and lower and upper confidence bounds on the anomaly.

If we look at the first line

``` csv
1850-01,-0.67456436,-0.98177195,-0.3673568
```

we can see that the time is `1850-01`, the anomaly `-0.67456436`, the lower confidence bound `-0.98177195`, and the upper confidence bound `-0.3673568`.

Now we understand the data format, let's see how to load it into Scala.
This will take several stages:

- we'll first load each line as a Scala `String`;
- we'll then split each line at the commas, turning each line into four values;
- we'll then need to ...

It seems a lot but we'll take it step-by-step and get there soon enough, and along the way we'll learn lots of interesting Scala programming techniques.
