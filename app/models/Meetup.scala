package models

import java.util.{Date}
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.json.Generic._

case class Meetup(id: Pk[Long] = NotAssigned, name: String, created: Date, happening: Option[Date], place: Option[String])

object Meetup {

  // -- Parsers
  
  /**
   * Parse a Meetup from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("meetup.id") ~
    get[String]("meetup.name") ~
    get[Date]("meetup.created") ~
    get[Option[Date]]("meetup.happening") ~
    get[Option[String]]("meetup.place") map {
      case id~name~created~happening~place => Meetup(id, name, created, happening, place)
    }
  }

  // -- Queries

  /**
   * Retrieve a meetup from the id.
   */
  def findById(id: Long): Option[Meetup] = {
    DB.withConnection { implicit connection =>
      SQL("select * from meetup where id = {id}").on('id -> id).as(Meetup.simple.singleOpt)
    }
  }

  /**
   * Return all meetups
   */
  def all(): List[Meetup] = {
    DB.withConnection { implicit connection =>
      SQL("select * from meetup").as(Meetup.simple *)
    }
  }

  /**
   * Update a meetup.
   *
   * @param id The meetup id
   * @param meetup The meetup values.
   */
  def update(id: Long, meetup: Meetup) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          update meetup
          set name = {name}, created = {created}, happening = {happening}, place = {place}
          where id = {id}
        """
      ).on(
        'id -> id,
        'name -> meetup.name,
        'created -> meetup.created,
        'happening -> meetup.happening,
        'place -> meetup.place
      ).executeUpdate()
    }
  }

  /**
   * Insert a new meetup.
   *
   * @param meetup The meetup values.
   */
  def insert(meetup: Meetup) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into meetup values (
            (select next value for meetup_seq), 
            {name}, {created}, {happening}, {place}
          )
        """
      ).on(
        'name -> meetup.name,
        'created -> meetup.created,
        'happening -> meetup.happening,
        'place -> meetup.place
      ).executeUpdate()
    }
  }

  /**
   * Delete a meetup.
   *
   * @param id Id of the meetup to delete.
   */
  def delete(id: Long) = {
    DB.withConnection { implicit connection =>
      SQL("delete from meetup where id = {id}").on('id -> id).executeUpdate()
    }
  }

  implicit def PkFormat[T](implicit fjs:Format[T]) = new Format[Pk[T]] {
    def reads(json: JsValue) = json match {
      case JsNull => NotAssigned
      case _ => Id(Json.fromJson[T](json))
    }
    def writes(key: Pk[T]) = key match {
      case NotAssigned => JsNull
      case Id(id) => Json.toJson(id)
    }
  }

  implicit object DateFormat extends Format[Date] {
    def reads(json: JsValue) = new Date(json.as[Long])
    def writes(date: Date) = new JsNumber(date.getTime())
  }

  /**
   * JSON conversion
   */
  implicit val MeetupFormat:Format[Meetup] = productFormat5("id", "name", "created", "happening", "place")(Meetup.apply)(Meetup.unapply)

}

