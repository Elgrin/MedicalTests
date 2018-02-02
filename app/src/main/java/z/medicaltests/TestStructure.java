package z.medicaltests;
import android.os.Parcel;
import android.os.Parcelable;

abstract class TestStructure implements Parcelable {

    TestStructure() {}

    public String getText() {
        return null;
    }
    public int getType() {
        return -1;
    }
    public String[] getOptions() {
        return null;
    }
    public boolean[] getFlags() {return null;}
    public String[] getParents(){return null;}
    public String[] getChildren(){return null;}
    public boolean[] getRelations(){return null;}
    public TestStructure getThis(){return this;}
    public int getID() {
        return -1;
    }

    public int describeContents() {
        return 0;
    }

}

class TestCheckBox extends TestStructure {
    private String Text;
    private int Type;
    private String Options[];
    private boolean Flags[];
    private int ID;

    TestCheckBox() {}
    public String getText() {
        return Text;
    }
    public int getType() {
        return Type;
    }
    public String[] getOptions() {
        return Options;
    }
    public boolean[] getFlags() {
        return Flags;
    }
    public TestCheckBox getThis(){return this;}
    public int getID() {
        return ID;
    }

    public void setText(String Text) {
        this.Text = Text;
    }
    void setType(int Type) {
        this.Type = Type;
    }
    void setOptions(String Options[]) {
        this.Options = Options;
    }
    public void setFlags(boolean Flags[]) {
        this.Flags = Flags;
    }
    void setID(int ID) {this.ID = ID;}

    protected TestCheckBox(Parcel in) {
        Text = in.readString();
        Type = in.readInt();
        Options = in.createStringArray();
        //in.readStringArray(Options);
        //in.readBooleanArray(Flags);
        Flags = in.createBooleanArray();
        ID = in.readInt();
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(Text);
        out.writeInt(Type);
        out.writeStringArray(Options);
        out.writeBooleanArray(Flags);
        out.writeInt(ID);
    }

    public static final Parcelable.Creator<TestCheckBox> CREATOR = new Parcelable.Creator<TestCheckBox>() {
        public TestCheckBox createFromParcel(Parcel in) {
            return new TestCheckBox(in);
        }
        public TestCheckBox[] newArray(int size) {
            return new TestCheckBox[size];
        }

    };
}

class MultipleChoices extends TestStructure{
    private String Text;
    private int Type;
    private String Parents[];
    private String Children[];
    private boolean Relations[];
    private int ID;

    MultipleChoices() {}
    public String getText() {return Text;}
    public int getType(){return Type;}
    public String[] getParents(){return Parents;}
    public String[] getChildren(){return Children;}
    public boolean[] getRelations(){return Relations;}
    public MultipleChoices getThis(){return this;}
    public int getID() {
        return ID;
    }

    public void setText(String Text) {this.Text=Text;}
    void setType(int Type){this.Type=Type;}
    void setParents(String Parents[]){this.Parents=Parents;}
    void setChildren(String Children[]){this.Children=Children;}
    void setRelations(boolean Relations[]){this.Relations=Relations;}
    void setID(int ID) {this.ID  = ID;}

    protected MultipleChoices(Parcel in) {
        Text = in.readString();
        Type = in.readInt();
        Parents = in.createStringArray();
        Children = in.createStringArray();
        Relations = in.createBooleanArray();
        //in.readStringArray(Parents);
        //in.readStringArray(Children);
        //in.readBooleanArray(Relations);
        ID = in.readInt();
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(Text);
        out.writeInt(Type);
        out.writeStringArray(Parents);
        out.writeStringArray(Children);
        out.writeBooleanArray(Relations);
        out.writeInt(ID);
    }

    public static final Parcelable.Creator<MultipleChoices> CREATOR = new Parcelable.Creator<MultipleChoices>() {
        public MultipleChoices createFromParcel(Parcel in) {
            return new MultipleChoices(in);
        }
        public MultipleChoices[] newArray(int size) {
            return new MultipleChoices[size];
        }

    };
}