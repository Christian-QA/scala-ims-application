package controllers

import controllers.CustomerController.findCustomerByName
import javax.inject._
import model.CustomerModel
import play.api.http.Writeable
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import play.twirl.api.Content
import reactivemongo.api.bson.BSONObjectID
import reactivemongo.api.commands.GetLastError.Acknowledged.w
import reactivemongo.io.netty.util.concurrent.Future


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  
  def explore(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.explore())
  }
  
  def tutorial(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.tutorial())
  }

  def datatest(id: Int): Action[AnyContent] = Action {
    Ok(s"stuff $id")
  }

//  def findCustomerByNameHome(name: String)(result: Writeable[CustomerModel])
//    (implicit w: Writeable[CustomerModel]): (Result) = {
//    val customer = findCustomerByName(name)
//    Ok(customer)
//  }

  println(findCustomerByName("borth"))


  //implicit def customerPlayWriter = Json.writes[CustomerModel]

  //implicit def customerPlayReader = Json.reads[CustomerModel]


}

