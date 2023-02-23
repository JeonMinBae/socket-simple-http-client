public enum HttpVersion {
    VER1_1("HTTP/1.1"),
    VER1_2("HTTP/1.2"),
    VER2("HTTP/2");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public String getVersion(){return this.version;}
}
