import React, { Component } from 'react';
import Comment from './Comment';
import { Link } from 'react-router-dom';
import AddResponse from './AddResponse';
import { connect } from 'react-redux';
import {fetchAddResponse} from '../../actions/comment/responsesActions';
import {Button, ButtonGroup} from 'reactstrap';
import "./Style.css";
class Comments extends Component{
  state={
    comment_id: null,
  }


  handleDeleteClick = id => (e)=>{
    this.props.deleteComment(id);
  }
  handleReplyClick = id => (e)=>{
    this.props.history.push("/responses/"+id);
  }

    commentList(){
      const id = this.state.comment_id;
      const comments= this.props.comments;

      const currentuser_id = this.props.user_id;
      const responses= this.props.responses;
      return comments.map(comment =>
        <section className= "commentSeccion">
          <div className="comment" key={comment.id}>
              <Comment comment={comment}/>
              <div className="box">
              <Link className= "ButtonB" to={"/responses/"+comment.id}> REPLY </Link>      
              {currentuser_id === comment.user_id ?(
                  <button className= "ButtonB" onClick={this.handleDeleteClick(comment.id)}> DELETE </button>
              ):(
                <div>
                </div>
              )}
              </div>
             { comment.responses ?(
                <div>
                  <p> </p>
                  <Link className= "ButtonB" to={{pathname:'/responses/'+ comment.id, state:{c:comment}}}> SHOW RESPONSES </Link>
                </div>
              ):(
                <div>
                </div>
              )}
          </div>
        </section>

        )

    }

    render(){

    return (
      <div className="post">
        <div className="comment-list">
          { this.commentList() }
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    responses: state.responses,
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    addResponse: (res) => {
      dispatch(fetchAddResponse(res))
    },

  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Comments)
