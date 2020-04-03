package com.mycompany.gatos_app;


public class GatosFavoritos {
    //Atributos
    private String id;
    private String imageId;
    private String apiKey = "731eacc4-dacc-400e-9877-c935d106281d";
    public Imagex image;
    
    //Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Imagex getImage() {
        return image;
    }

    public void setImage(Imagex image) {
        this.image = image;
    }
    
}
