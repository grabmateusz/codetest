import { Injectable } from '@angular/core';
import { ErrorMessage } from '../model/error-message';

@Injectable({
  providedIn: 'root'
})
export class ErrorMessageService {

  emitErrorMessage(error, errorChanged) {
    const errorMessage = new ErrorMessage(error.message);
    errorChanged.emit(errorMessage);
  }
}
