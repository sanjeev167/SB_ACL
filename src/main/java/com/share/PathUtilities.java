/**
 * 
 */
package com.share;

/**
 * Utility class for working with request paths.
 *
 * @author Sanjeev
 */
public final class PathUtilities
{
  private static final String WILDCARD = "*";

  /**
   * Match a path which may contain a wildcard.
   *
   * @param requestPath The request path submitted by the client
   * @param exPath The match path with * wildcard
   *
   * @return DOCME
   */
  public static boolean match(String requestPath, String wildcardPath)
  {
    //  *somestuffhereverylong*  != stuff
    if( wildcardPath.length() - 2 > requestPath.length())
    {
      return false;
    }
    
    //log.info("match(" + requestPath + "," + exPath + ")");
    int wildcardIndex = wildcardPath.indexOf(WILDCARD);

    if (wildcardIndex == -1)
    {
      return requestPath.equalsIgnoreCase(wildcardPath);
    }
    else if( wildcardPath.charAt(0) == '*' && wildcardPath.charAt(wildcardPath.length()-1) == '*' )
    {
      String path = wildcardPath.substring(1,wildcardPath.length()-1);
      return requestPath.indexOf(path) > -1;
    }
    else if (wildcardIndex == (wildcardPath.length() - 1)) //ends with *
    {
        //log.info("Wildcard appears at end of match path.");
        String checkString = wildcardPath.substring(0, wildcardPath.length() - 1);

        //  /stuff/* -> /stuff     /stuff/abc* != /stuff/ab
        
        if( checkString.charAt(checkString.length()-1) == '/')
        {
          checkString = checkString.substring(0,checkString.length() - 1);
        }
        //log.info("String after wildcard removed: " + checkString);
        boolean answer = requestPath.startsWith(checkString);

        //log.info("Does " + requestPath + " start with " + checkString + "? " + answer);
        return answer;
    }
    else if( wildcardPath.charAt(0) == '*')
    {
      String checkString = wildcardPath.substring(1);

      //log.info("String after wildcard removed: " + checkString);
      boolean answer = requestPath.endsWith(checkString);
      return answer;
    }
    else
    {
      //log.info("Wildcard appears in the middle of the string");
      String preMatch = wildcardPath.substring(0, wildcardIndex);
      String postMatch = wildcardPath.substring(wildcardIndex + 1);

      return requestPath.startsWith(preMatch) && requestPath.endsWith(postMatch);
    }
  }
}
