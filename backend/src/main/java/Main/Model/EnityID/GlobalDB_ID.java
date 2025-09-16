//package Main.Model.EnityID;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Embeddable
//    public class GlobalDB_ID implements Serializable {
//
//        @Column(name = "major_code", length =5)
//        private String majorCode;
//
//        @Column(name = "subject_code", length = 10)
//        private String subjectCode;
//
//    public  GlobalDB_ID(){};
//
//    public GlobalDB_ID(String majorCode, String subjectCode){
//        this.majorCode = majorCode;
//        this.subjectCode = subjectCode;
//    }
//
//    public void setMajorCode(String majorCode) {
//        this.majorCode = majorCode;
//    }
//    public String getMajorCode() {
//        return majorCode;
//    }
//
//    public void setSubjectCode(String subjectCode) {
//        this.subjectCode = subjectCode;
//    }
//    public String getSubjectCode() {
//        return subjectCode;
//    }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            GlobalDB_ID that = (GlobalDB_ID) o;
//            return Objects.equals(majorCode, that.majorCode) &&
//                    Objects.equals(subjectCode, that.subjectCode);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(majorCode, subjectCode);
//        }
//}
//
//
