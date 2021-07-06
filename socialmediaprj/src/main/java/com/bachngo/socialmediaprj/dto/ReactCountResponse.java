package com.bachngo.socialmediaprj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactCountResponse {
	
	public Long likes;
	public Long disLikes;
}
