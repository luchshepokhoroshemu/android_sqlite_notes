package meojike.com.android_sqlite_notes.database;

import java.util.Objects;

public class NoteDataModel {
    private String name;
    private String noteContent;

    public NoteDataModel(String name, String noteContent) {
        this.name = name;
        this.noteContent = noteContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, noteContent);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        NoteDataModel noteDataModel = (NoteDataModel) obj;

        return Objects.equals(name, noteDataModel.name) && Objects.equals(noteContent, noteDataModel.noteContent);
    }

    @Override
    public String toString() {
        return "NoteDataModel{name=\"" + name + "\", content=\"" + noteContent + "\"}";
    }
}
