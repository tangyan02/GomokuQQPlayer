enum State {

    CALCULATING("计算中"),

    WAITING("抓图中"),

    PREPARING("准备就绪");

    private String description;

    State(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}