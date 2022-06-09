import { useEffect, useState } from 'react';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import axios from 'axios';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface modalSwitch {
  open: boolean;
  Close: () => void;
  Change: (data: ResponseIdName[]) => void;
}

const style = {
  position: 'absolute' as const,
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  borderRadius: 10,
  boxShadow: 24,
  p: 4,
};
export default function RepositoryModal({ open, Close, Change }: modalSwitch) {
  const [errorId, setErrorId] = useState(false);
  const [errorPassword, setErrorPassword] = useState(false);
  const [errorUserName, setErrorUserName] = useState(false);

  const [id, setId] = useState('');
  const [passWord, setPassWord] = useState('');
  const [userName, setUserName] = useState('');

  const handleIdChange = (event: any) => {
    setId(event.target.value);
    setErrorId(false);
  };

  const handlePassWordChange = (event: any) => {
    setPassWord(event.target.value);
    setErrorPassword(false);
  };
  const handleUserNameChange = (event: any) => {
    setUserName(event.target.value);
    setErrorUserName(false);
  };

  const handleSaveClick = () => {
    if (!id || !passWord || !userName) {
      if (!id) setErrorId(true);
      if (!passWord) setErrorPassword(true);
      if (!userName) setErrorUserName(true);

      return;
    }

    const data = {
      email: id,
      password: passWord,
      username: userName,
    };

    axios
      .post('/api/git/account', data)
      .then((res) => {
        Change(res.data);
        setId('');
        setPassWord('');
        setUserName('');
        Close();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleCloseClick = () => {
    setId('');
    setPassWord('');
    setUserName('');
    Close();
  };
  return (
    <Modal
      open={open}
      onClose={Close}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Box position="relative" sx={{ top: 20, left: 10 }}>
          <Paper sx={{ padding: 1, textAlign: 'center', width: 250 }}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
              Repository Credentials
            </Typography>
          </Paper>
        </Box>
        <Paper
          sx={{ padding: 3, pt: 4, borderWidth: 3 }}
          elevation={0}
          variant="outlined"
        >
          <Typography id="modal-modal-description" sx={{ my: 2 }}>
            UserName with Password
          </Typography>
          <Typography>ID</Typography>
          <TextField
            fullWidth
            error={errorId}
            helperText={errorId ? '아이디를 적어주어주세요' : ''}
            label="ID"
            InputLabelProps={{
              sx: { color: 'rgb(200,200,200)' },
            }}
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            InputProps={{ sx: { fontWeight: 'bold' } }}
            placeholder="ID"
            onChange={handleIdChange}
          />
          <Typography>Password</Typography>
          <TextField
            fullWidth
            error={errorPassword}
            helperText={errorPassword ? '비밀번호를 적어주어주세요' : ''}
            label="Password"
            InputLabelProps={{
              sx: { color: 'rgb(200,200,200)' },
            }}
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            InputProps={{ sx: { fontWeight: 'bold' } }}
            placeholder="Password"
            type="password"
            autoComplete="current-password"
            onChange={handlePassWordChange}
          />
          <Typography>UserName</Typography>
          <TextField
            fullWidth
            error={errorUserName}
            helperText={errorUserName ? '이름를 적어주어주세요' : ''}
            label="UserName"
            InputLabelProps={{
              sx: { color: 'rgb(200,200,200)' },
            }}
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            InputProps={{ sx: { fontWeight: 'bold' } }}
            placeholder="UserName"
            onChange={handleUserNameChange}
          />
          <Stack mt={3} direction="row" justifyContent="flex-end" spacing={2}>
            <Button
              variant="outlined"
              size="small"
              onClick={handleSaveClick}
              sx={{ color: 'black', borderColor: 'black' }}
            >
              Save
            </Button>
            <Button
              variant="outlined"
              size="small"
              onClick={handleCloseClick}
              sx={{ color: 'black', borderColor: 'black' }}
            >
              Cancel
            </Button>
          </Stack>
        </Paper>
      </Box>
    </Modal>
  );
}
