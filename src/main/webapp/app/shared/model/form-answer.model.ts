import dayjs from 'dayjs';
import { IAnswer } from 'app/shared/model/answer.model';
import { IForms } from 'app/shared/model/forms.model';

export interface IFormAnswer {
  id?: string;
  creationForm?: string | null;
  modifiedForm?: string | null;
  answers?: IAnswer[] | null;
  forms?: IForms | null;
}

export const defaultValue: Readonly<IFormAnswer> = {};
