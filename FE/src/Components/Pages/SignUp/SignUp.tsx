import {
  makeStyles,
  Container,
  Typography,
  TextField,
  Button,
} from '@material-ui/core';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useState } from 'react';
import styled from '@emotion/styled';
import { unset } from 'lodash';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';

interface IFormInput {
  principal: string;
  name: string;
  credential: string;
  passwordConfirm: string;
  authKey: string;
}

interface ResponsId {
  name: string;
  state: string;
  principal: string;
}

const schema = yup.object().shape({
  principal: yup
    .string()
    .required('아이디는 필수 입니다.')
    .min(2, '2자 이상 입력하세요.')
    .max(25, '25자 이하로 입력하세요.')
    .matches(/^[a-zA-Z0-9]*$/, '영문과 숫자만 입력 가능합니다.'),
  name: yup
    .string()
    .required('이름은 필수 입니다.')
    .min(2, '2자 이상 입력하세요.')
    .max(25, '25자 이하로 입력하세요.'),
  credential: yup
    .string()
    .required('비밀번호는 필수 입니다.')
    .min(8, '8자 이상 입력하세요.')
    .max(30, '30자 이하로 입력하세요.')
    .matches(
      /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W))(?=.*[!@#$%^*+=-]).{8,16}$/,
      '비밀번호는 반드시 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.',
    ),
  passwordConfirm: yup
    .string()
    .oneOf([yup.ref('credential'), null], '비밀번호가 일치하지 않습니다.'),
  authKey: yup.string().required('부여받은 인증키를 입력하세요'),
});

const useStyles = makeStyles((theme) => ({
  heading: {
    textAlign: 'center',
    margin: theme.spacing(1, 0, 4),
  },
  submitButton: {
    marginTop: theme.spacing(4),
  },
}));

export default function SignUp() {
  const [inputId, setInputId] = useState<string>('');
  const [inputName, setInputName] = useState<string>('');

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<IFormInput>({
    resolver: yupResolver(schema),
  });

  const { heading, submitButton } = useStyles();
  const history = useHistory();

  const onSubmit = (data: IFormInput) => {
    unset(data, 'passwordConfirm');
    axios.post(`/api/user/signup`, data).then((res) => {
      const data = res.data as ResponsId;
      if (data.state === 'Success') history.push('/login');
    });
  };

  const handleChangeId = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputId(event.currentTarget.value);
  };

  const handleCheckId = () => {
    const params = { id: inputId };
    axios.post(`/api/user/duplicate/id`, null, { params }).then((res) => {
      if (res.data.state === 'Fail' || inputId === '') {
        alert('사용할 수 없는 아이디 입니다.');
        setInputId('');
      } else {
        alert('사용 가능한 아이디 입니다.');
      }
    });
  };

  const handleChangeName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputName(event.currentTarget.value);
  };

  const checkName = (name: string | undefined) => {
    const params = { name };
    axios.post(`/api/user/duplicate/name`, null, { params }).then((res) => {
      if (res.data.state === 'Fail') {
        alert('사용할 수 없는 이름 입니다.');
        setInputName('');
      } else alert('사용 가능한 이름 입니다.');
    });
  };

  return (
    <Container maxWidth="xs">
      <Stack
        mt={5}
        direction="column"
        justifyContent="center"
        alignItems="center"
        spacing={2}
      >
        <Typography className={heading} variant="h3">
          회원가입
        </Typography>
        <form onSubmit={handleSubmit(onSubmit)} noValidate>
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('principal')}
            variant="outlined"
            margin="normal"
            label="ID"
            placeholder="영문과 숫자만 입력"
            helperText={errors.principal?.message}
            error={!!errors.principal?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
            onChange={handleChangeId}
            value={inputId}
          />
          <Button color="primary" onClick={handleCheckId}>
            ID 중복 확인
          </Button>
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('credential')}
            variant="outlined"
            margin="normal"
            label="비밀번호"
            placeholder="8~16자이며, 영문, 숫자, 특수문자를 포함해서 입력"
            helperText={errors.credential?.message}
            error={!!errors.credential?.message}
            type="password"
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('passwordConfirm')}
            variant="outlined"
            margin="normal"
            label="비밀번호 확인"
            placeholder="비밀번호와 똑같이 입력"
            helperText={errors.passwordConfirm?.message}
            error={!!errors.passwordConfirm?.message}
            type="password"
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('name')}
            variant="outlined"
            margin="normal"
            label="이름"
            placeholder="2자 이상 25자 이하로 입력"
            helperText={errors.name?.message}
            error={!!errors.name?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
            onChange={handleChangeName}
            value={inputName}
          />
          <Button
            color="primary"
            onClick={() => {
              checkName(inputName);
            }}
          >
            이름 중복 확인
          </Button>
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('authKey')}
            variant="outlined"
            margin="normal"
            label="인증키"
            helperText={errors.authKey?.message}
            error={!!errors.authKey?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={submitButton}
          >
            가입하기
          </Button>
        </form>
      </Stack>
    </Container>
  );
}
