import React from 'react';
import './Style.css';
import { Link } from 'react-router-dom';

const ListCompilationChallengeModify = ({ listCompilationChallenge }) => {
  
  const compilationChallengeList = listCompilationChallenge.map(challenge => {
    
    return (
      <div className="hacker card" key={challenge.id}>
        <div className="card-content">
          <span className="card-title">
            <p style={{ color: '#F44336',fontWeight: 'bold' }}> { challenge.title }</p>
          </span>
          <div> id: <p style={{ color: '#F44336' }}>{challenge.id} </p> </div>
          <div>description: <p style={{ color: '#F44336' }}>{challenge.description} </p></div>
          <div>poit: <p style={{ color: '#F44336' }}>{challenge.point}</p></div>
          <div>class name: <p style={{ color: '#F44336' }}>{challenge.class_name}</p></div>
          <div>source: <p style={{ color: '#F44336' }}>{challenge.source}</p></div>
          <Link 
            className="button-table" 
            to={{
              pathname:"/modify",
              query:{id: challenge.id, typeTest: false}
            }}> MODIFY </Link>
        </div>
      </div>
    )
  });
  return (
    <div className="post">
      <div className="hacker-list">
        {compilationChallengeList}
      </div>
    </div>
  );
}

export default ListCompilationChallengeModify;