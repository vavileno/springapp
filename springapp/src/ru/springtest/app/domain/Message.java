package ru.springtest.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.TableGenerator;

@Entity(name="_message")
public class Message {
	
    @Id
    @GeneratedValue(generator = "TableGenerator")
    @GenericGenerator(name = "TableGenerator", strategy = "org.hibernate.id.enhanced.TableGenerator",
                      parameters = {
        @Parameter(name = TableGenerator.SEGMENT_VALUE_PARAM, value = "_message")
    })
    @Column(name = "_id")	
	private Integer id;
	
    @Column(name = "_content")
	private String content;
	
    @Column(name = "_message_date")
    @Temporal(TemporalType.DATE)
	private Date messageDate;
    
    @JoinColumn(name = "_user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @LazyToOne(LazyToOneOption.FALSE)
    private User user;
	
	public Message() {
	}
	
	public Message(String content, User user) {
		this(content, user, null);
	}
	
	public Message(String content, User user, Date messageDate) {
		this.content = content;
		this.messageDate = messageDate == null ? new Date() : messageDate;
		this.user = user;
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
