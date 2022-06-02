package com.aah.sftelehealthworker.models.document;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Document implements Parcelable {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("patientId")
    @Expose
    int patientId;
    @SerializedName("documentCategoryId")
    @Expose
    int documentCategoryId;
    @SerializedName("uploadProgress")
    @Expose
    int uploadProgress;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("note")
    @Expose
    String note;
    @SerializedName("createdAt")
    @Expose
    String createdAt;
    @SerializedName("updatedAt")
    @Expose
    String updatedAt;
    @SerializedName("documentUploadId")
    @Expose
    String documentUploadId;
    @SerializedName("thumbnail")
    @Expose
    String thumbnail = "http://www.simpopdf.com/sample/image-to-pdf-sample.jpg";
    @SerializedName("previewUrl")
    @Expose
    String previewUrl;
    @SerializedName("type")
    @Expose
    String documentType;

    // set to true if the document is already shared with the doctor
    boolean isShared = false;

    // set to true is the document is chosen to be shared by the user
    boolean isSelected = false;

    public Document() {}


    protected Document(Parcel in) {
        id = in.readInt();
        name = in.readString();
        patientId = in.readInt();
        documentCategoryId = in.readInt();
        uploadProgress = in.readInt();
        title = in.readString();
        url = in.readString();
        note = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        documentUploadId = in.readString();
        thumbnail = in.readString();
        previewUrl = in.readString();
        documentType = in.readString();
        isShared = in.readByte() != 0;
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(patientId);
        dest.writeInt(documentCategoryId);
        dest.writeInt(uploadProgress);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(note);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(documentUploadId);
        dest.writeString(thumbnail);
        dest.writeString(previewUrl);
        dest.writeString(documentType);
        dest.writeByte((byte) (isShared ? 1 : 0));
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDocumentCategoryId() {
        return documentCategoryId;
    }

    public void setDocumentCategoryId(int documentCategoryId) {
        this.documentCategoryId = documentCategoryId;
    }

    public int getUploadProgress() {
        return uploadProgress;
    }

    public void setUploadProgress(int uploadProgress) {
        this.uploadProgress = uploadProgress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        // if a document is already shared then it must not be selected to be shared again
        if(this.isShared)
            isSelected = false;
        else
            isSelected = selected;
    }

    public String getDocumentUploadId() {
        return documentUploadId;
    }

    public void setDocumentUploadId(String documentUploadId) {
        this.documentUploadId = documentUploadId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getDocumentType() {
        String dataType = "jpg";
        int i = this.getPreviewUrl().lastIndexOf('.');
        if (i > 0) {
            dataType = this.getPreviewUrl().substring(i+1);
        }
        return (dataType.equalsIgnoreCase("pdf") ? "pdf" : "image");
    }

    public String getDocumentExtension() {
        String dataType = "jpg";
        int i = this.getPreviewUrl().lastIndexOf('.');
        if (i > 0) {
            dataType = this.getPreviewUrl().substring(i+1);
        }
        return dataType;
    }


}
