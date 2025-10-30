package com.example.question.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTypeSpecificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.question.Question;
import com.example.question.QuestionWrapper;
import com.example.question.Response;
import com.example.question.Dao.QuestionDao;

@Service
public class QuestionService {
   @Autowired
	QuestionDao questionDao;
	
	public  ResponseEntity<List<Question>> getAllQuestions() {
		
		try {
		return new ResponseEntity<List<Question>>(questionDao.findAll(),HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Question>>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
			return new ResponseEntity<List<Question>>(questionDao.findByCategory(category),HttpStatus.OK);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		return new ResponseEntity<List<Question>>(questionDao.findByCategory(category),HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<String> addQuestion(Question question) {
		try {
			questionDao.save(question);
			return new ResponseEntity<String>("success",HttpStatus.CREATED);
						}
			catch(Exception e) {
				e.printStackTrace();
			}
		return new ResponseEntity<String>("Failed to save question",HttpStatus.INTERNAL_SERVER_ERROR);		
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
		List<Integer> list=questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {
		List<QuestionWrapper>  wrappers=new ArrayList<>();
		List<Question> questions=new ArrayList<>();
		for(Integer i:questionIds) {
			questions.add(questionDao.findById(i).get());
		}
		for(Question q:questions) {
			QuestionWrapper w=new QuestionWrapper();
			w.setId(q.getId());
			w.setQuestionTitle(q.getQuestionTitle());
			w.setOption1(q.getOption1());
			w.setOption2(q.getOption2());
			w.setOption3(q.getOption3());
			w.setOption4(q.getOption4());
			wrappers.add(w);
		}
		return new ResponseEntity<List<QuestionWrapper>>(wrappers,HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int right=0;
		for(Response r:responses) {
			Question q=questionDao.findById(r.getId()).get();
			if(r.getResponse().equals(q.getRightAnswer())) {
				right++;
			}
		}
		return new ResponseEntity<Integer>(right,HttpStatus.OK);
	}
	
}





