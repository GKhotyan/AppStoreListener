package utils

interface PageParser {
    String getVersionFromHtmlByUrl(String url) throws IOException
}