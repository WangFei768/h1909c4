package cn.jiyun.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class QueryVo {
	private Integer sid;
	private String sname;
	private String sex;
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	private String age;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	private String img;
	private Integer mid;
private String mname;

@Override
public String toString() {
	return "QueryVo [sid=" + sid + ", sname=" + sname + ", sex=" + sex + ", age=" + age + ", birthday=" + birthday
			+ ", img=" + img + ", mid=" + mid + ", mname=" + mname + "]";
}

public String getMname() {
	return mname;
}

public void setMname(String mname) {
	this.mname = mname;
}

}
