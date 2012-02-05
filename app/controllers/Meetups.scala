package controllers

import play.api._
import play.api.libs.json._
import play.api.mvc._
import models.Meetup

object Meetups extends Controller {

  def all = Action {
    Ok(Json.toJson(Meetup.all))
  }

}
