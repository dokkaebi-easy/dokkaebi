import React, { useState } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';

interface NginxProps {
  value: string;
  index: number;
  domainValue: string[];
  DelClick: (index: number) => void;
}

export default function DomainBox({
  value,
  index,
  domainValue,
  DelClick,
}: NginxProps) {
  const [domain, setDomain] = useState(value);

  const handleDomainChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDomain(event.target.value);
    domainValue[index] = event.target.value;
  };

  const handleDelClick = () => {
    DelClick(index);
  };
  return (
    <Grid container spacing={2}>
      <Grid item xs={2} sx={{ margin: 'auto auto' }}>
        <Typography align="center">Domain URL</Typography>
      </Grid>
      <Grid item xs={8}>
        <TextField
          fullWidth
          label="Project Name"
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          placeholder="ProjectName"
          defaultValue={domain}
          onChange={handleDomainChange}
        />
      </Grid>
      <Grid item xs={2} sx={{ marginBottom: 1, marginTop: 'auto' }}>
        <Button
          variant="outlined"
          startIcon={<DeleteIcon />}
          size="small"
          onClick={handleDelClick}
          sx={{
            color: 'red',
            borderColor: 'red',
          }}
        >
          Delete
        </Button>
      </Grid>
    </Grid>
  );
}
